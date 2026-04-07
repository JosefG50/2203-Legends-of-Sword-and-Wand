extends CharacterBody2D
class_name Player

@onready var money_label = $CanvasLayer/StaticUI/VBoxContainer/MoneyRow/MoneyLabel
@onready var exp_label = $CanvasLayer/StaticUI/VBoxContainer/EXPRow/EXPLabel
@onready var level_label = $CanvasLayer/StaticUI/VBoxContainer/LevelRow/LevelLabel
@onready var AnimationSprite2Dcharacter = $AnimatedSprite2D

@export var money: int = 0
@export var experience: int = 0
@export var level: int = 1
@export var exp_to_level_up: int = 100

@onready var camerastt = $Camera2D

const SPEED = 100

func _physics_process(_delta: float) -> void:
	var direction := Input.get_vector("ui_left", "ui_right", "ui_up", "ui_down")
	
	if direction != Vector2.ZERO:
		velocity = direction * SPEED
		AnimationSprite2Dcharacter.play("walk")
		AnimationSprite2Dcharacter.flip_h = direction.x < 0
	else:
		velocity = velocity.move_toward(Vector2.ZERO, SPEED)
		AnimationSprite2Dcharacter.play("idle")

	move_and_slide()

func _ready():
	update_stats_ui()

func add_money(amount):
	money += amount
	update_stats_ui()

func add_exp(amount: int):
	experience += amount
	while experience >= exp_to_level_up:
		experience -= exp_to_level_up
		level += 1
		print("Level Up! Current Level: ", level)
	update_stats_ui()

func add_level(amount):
	level += amount
	update_stats_ui()

func update_stats_ui():
	money_label.text = "Gold: " + str(money)
	exp_label.text = "EXP: " + str(experience)
	level_label.text = "LVL: " + str(level)



func _on_battle_box_body_entered(body: Node2D) -> void:
	if body == self:
		var battle_node = get_parent().get_node("battle")
		
		if battle_node:
			set_physics_process(false)
			camerastt.enabled = false 
			if not battle_node.battle_ended.is_connected(_on_battle_finished):
				battle_node.battle_ended.connect(_on_battle_finished)
			
			battle_node.start_battle()

func _on_battle_finished():
	camerastt.enabled = true 
	set_physics_process(true)
	global_position.x -= 30 
	print("Player is back to exploring!")
