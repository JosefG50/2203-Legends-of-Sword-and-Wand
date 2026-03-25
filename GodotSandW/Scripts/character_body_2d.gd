extends CharacterBody2D
# Reference your UI labels
@onready var money_label = $CanvasLayer/StaticUI/VBoxContainer/MoneyRow/MoneyLabel
@onready var exp_label = $CanvasLayer/StaticUI/VBoxContainer/EXPRow/EXPLabel
@onready var level_label = $CanvasLayer/StaticUI/VBoxContainer/LevelRow/LevelLabel
@onready var AnimationSprite2Dcharacter = $AnimatedSprite2D

const SPEED = 100

func _physics_process(_delta: float) -> void:
	# Get input for both axes
	var direction := Input.get_vector("ui_left", "ui_right", "ui_up", "ui_down")
	
	if direction != Vector2.ZERO:
		velocity = direction * SPEED
		# --- ANIMATION LOGIC ---
		AnimationSprite2Dcharacter.play("walk")
		
		# Flip the sprite horizontally based on left/right movement
		if direction.x < 0:
			AnimationSprite2Dcharacter.flip_h = true
		elif direction.x > 0:
			AnimationSprite2Dcharacter.flip_h = false
	else:
		velocity = velocity.move_toward(Vector2.ZERO, SPEED)
		# --- ANIMATION LOGIC ---
		AnimationSprite2Dcharacter.play("idle")

	move_and_slide()




var money = 0
var experience = 0
var level = 0

func _ready():
	update_stats_ui()

# Call this whenever you loot gold or win a battle
func add_money(amount):
	money += amount
	update_stats_ui()

func add_exp(amount):
	experience += amount
	update_stats_ui()

func add_level(amount):
	level += amount
	update_stats_ui()

func update_stats_ui():
	money_label.text = "Gold: " + str(money)
	exp_label.text = "EXP: " + str(experience)
	level_label.text = "LVL" + str(level)
