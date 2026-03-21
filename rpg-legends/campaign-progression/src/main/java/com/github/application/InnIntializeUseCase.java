public class innIntializeUseCase {
    private PartyService partyService;

    public innIntializeUseCase(PartyService partyService) {
        this.partyService = partyService;
    }

    public void execute() {
        partyService.maxRestore();
    }
}