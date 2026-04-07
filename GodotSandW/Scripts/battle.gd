extends Node2D

signal battle_ended

@onready var ally = $AlliesAnim
@onready var enemy = $EnemiesAnim
@onready var ally_pos = $Allies/Pos_Front.global_position
@onready var enemy_pos = $Enemies/Pos_Front.global_position
@onready var battle_camera = $Camera2D

var hp_ally = 100
var hp_enemy = 100

func _ready():
	self.hide()


func start_battle():
	self.show()

	
	ally.global_position = ally_pos
	enemy.global_position = enemy_pos
	
	hp_ally = 100
	hp_enemy = 100
	enemy.modulate.a = 1.0 
	print("Battle Start!")

func _on_attack_pressed():
	if hp_ally <= 0 or hp_enemy <= 0: return

	hp_enemy -= 10
	_flash(enemy)
	print("Enemy HP: ", hp_enemy)
	
	if hp_enemy <= 0:
		_finish(true)
	else:
	
		await get_tree().create_timer(0.4).timeout
		hp_ally -= 10
		_flash(ally)
		print("Ally HP: ", hp_ally)
		if hp_ally <= 0: _finish(false)

func _flash(target):
	var t = create_tween()
	t.tween_property(target, "modulate", Color.RED, 0.1)
	t.tween_property(target, "modulate", Color.WHITE, 0.1)

func _finish(won):
	if won:
		print("Victory!")
		var p = get_tree().get_first_node_in_group("player")
		if p: p.add_exp(20)
	else:
		print("Defeat!")

	await get_tree().create_timer(1.0).timeout
	battle_camera.enabled = false
	self.hide()
	battle_ended.emit()
