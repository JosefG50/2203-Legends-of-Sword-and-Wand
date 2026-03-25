extends Node2D

# Reference the two different master sprites
@onready var allies_master = $AlliesAnim
@onready var enemies_master = $EnemiesAnim

# Reference the marker containers
@onready var allies_container = $Allies
@onready var enemies_container = $Enemies

func _ready():
	# Hide the master templates so they don't sit in the middle of the screen
	allies_master.visible = false
	enemies_master.visible = false

func _input(event):
	# Allies (Up/Down)
	if event.is_action_pressed("ui_up"):
		spawn_unit(allies_container, allies_master)
	elif event.is_action_pressed("ui_down"):
		remove_unit(allies_container)

	# Enemies (Left/Right)
	if event.is_action_pressed("ui_left"):
		spawn_unit(enemies_container, enemies_master)
	elif event.is_action_pressed("ui_right"):
		remove_unit(enemies_container)

func spawn_unit(container, master_template):
	# 1. Get all Marker2D nodes in this container
	var markers = []
	for child in container.get_children():
		if child is Marker2D:
			markers.append(child)
	
	# 2. Count how many AnimatedSprite2Ds we already spawned here
	var spawned_count = 0
	for child in container.get_children():
		if child is AnimatedSprite2D:
			spawned_count += 1
			
	# 3. If we have an empty marker, spawn the clone
	if spawned_count < markers.size():
		var new_sprite = master_template.duplicate()
		new_sprite.visible = true
		
		# Add as child of the container
		container.add_child(new_sprite)
		
		# Snap to the specific marker's position
		new_sprite.position = markers[spawned_count].position
		
		# Ensure it starts playing its idle
		new_sprite.play("idle")

func remove_unit(container):
	# Find the last sprite added and delete it
	var clones = []
	for child in container.get_children():
		if child is AnimatedSprite2D:
			clones.append(child)
			
	if clones.size() > 0:
		clones[-1].queue_free()
