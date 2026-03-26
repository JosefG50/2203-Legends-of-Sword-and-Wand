package com.github.campaign_progression.domain;

/**
 * Manages saving and restoring the overall campaign state by coordinating between
 * the {@link CampaignManager}, {@link PartyService}, and {@link InventoryService}.
 *
 * <p>This class acts as a facade for snapshot operations — it can serialize the
 * current campaign into a {@link CampaignSnapshot} and restore a previous state
 * from one. It is the primary entry point for save/load functionality.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * CampaignStateManager stateManager = new CampaignStateManager(
 *     campaignManager, partyService, inventoryService, inn
 * );
 *
 * // Save
 * CampaignSnapshot snapshot = stateManager.createSnapshot();
 *
 * // Load
 * stateManager.loadFromSnapshot(snapshot);
 * }</pre>
 */
public class CampaignStateManager {

    /** Manages room progression and battle chance for the active campaign. */
    private final CampaignManager campaignManager;

    /** Provides access to party composition and hero management. */
    private final PartyService partyService;

    /** Provides access to the player's gold and item inventory. */
    private final InventoryService InventoryService;

    /**
     * Constructs a new {@code CampaignStateManager} with the required service dependencies.
     *
     * <p>Note: the {@code inn} parameter is accepted but not currently stored.
     * It is reserved for future use (e.g. healing/resting logic on load).</p>
     *
     * @param campaignManager  the manager tracking room progression and battle state; must not be {@code null}
     * @param partyService     the service managing the player's party of heroes; must not be {@code null}
     * @param InventoryService the service managing gold and item inventory; must not be {@code null}
     * @param inn              reserved for future use; may be {@code null}
     */
    public CampaignStateManager(CampaignManager campaignManager,
                                PartyService partyService,
                                InventoryService InventoryService,
                                Inn inn) {
        this.campaignManager = campaignManager;
        this.partyService = partyService;
        this.InventoryService = InventoryService;
    }

    /**
     * Restores the campaign state from a given {@link CampaignSnapshot}.
     *
     * <p>The following state is restored in order:</p>
     * <ol>
     *   <li>Battle chance is set on the {@link CampaignManager}</li>
     *   <li>Rooms are advanced sequentially until the snapshot's room counter is reached,
     *       preserving the room generation sequence</li>
     *   <li>Gold and items are applied to the {@link InventoryService}</li>
     *   <li>The party is cleared and repopulated from the snapshot's {@link HeroState} list</li>
     * </ol>
     *
     * <p><b>Note:</b> This method mutates the state of all injected services. It assumes
     * {@code HeroState} objects can be passed directly to {@link PartyService#addHero(HeroState)}.</p>
     *
     * @param snapshot the campaign snapshot to restore from; must not be {@code null}
     * @throws IllegalArgumentException if {@code snapshot} is {@code null}
     * @throws IllegalStateException    if advancing rooms exceeds the campaign's maximum room limit
     */
    public void loadFromSnapshot(CampaignSnapshot snapshot) {
        if (snapshot == null) throw new IllegalArgumentException("Snapshot cannot be null");

        campaignManager.setBattleChance((float) snapshot.getBattleChance());

        while (campaignManager.getRoomCounter() < snapshot.getRoomCounter()) {
            campaignManager.nextRoom(); // ensures room sequence
        }

        InventoryService.gainGold(snapshot.getGold());
        InventoryService.setItems(snapshot.getItems());

        partyService.getParty().clear();
        for (HeroState hi : snapshot.getParty()) {
            partyService.addHero(hi); // assumes HeroState -> HeroState
        }
    }

    /**
     * Captures the current campaign state and returns it as a {@link CampaignSnapshot}.
     *
     * <p>The snapshot includes:</p>
     * <ul>
     *   <li>Current room counter and battle chance from the {@link CampaignManager}</li>
     *   <li>A string representation of the current room (via {@code toString()})</li>
     *   <li>Current gold from the {@link InventoryService}</li>
     *   <li>Total party levels from the {@link PartyService} (used in place of raw EXP)</li>
     *   <li>The current party list and item array</li>
     * </ul>
     *
     * <p><b>TODO:</b> Consider replacing {@code getCurrentRoom().toString()} with a proper
     * room ID, and replacing {@code getTotalLevels()} with an explicit EXP value if needed.</p>
     *
     * @return a new {@link CampaignSnapshot} reflecting the current campaign state
     */
    public CampaignSnapshot createSnapshot() {
        return new CampaignSnapshotImpl(
                campaignManager.getRoomCounter(),
                campaignManager.getBattleChance(),
                campaignManager.getCurrentRoom().toString(), // or serialize room ID
                InventoryService.getGold(),
                partyService.getTotalLevels(),               // or EXP if needed
                partyService.getParty(),
                InventoryService.getItems()
        );
    }
}