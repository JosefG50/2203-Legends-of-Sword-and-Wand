extends Node2D

@onready var TopDoorMark = $TopDoor
@onready var BotDoorMark = $BotDoor
@onready var LeftDoorMark = $LeftDoor
@onready var RightDoorMark = $RightDoor
@onready var XDoorSprite = $XDoorSprite
@onready var YDoorSprite = $YDoorSprite
@onready var AreaX = $AreaXDoor
@onready var AreaY = $AreaYDoor
@onready var Floor = $Floor
@onready var player = $Player

func _ready() -> void:
	# Initial room setup
	spawn_verticledoor_at(TopDoorMark.global_position)
	spawn_horizontaldoor_at(RightDoorMark.global_position)
	
	# Spawn the trigger paths
	spawn_verticlepath_at(BotDoorMark.global_position)
	spawn_horizontalpath_at(LeftDoorMark.global_position)

# --- SPAWN FUNCTIONS WITH SIGNAL CONNECTIONS ---

func spawn_horizontalpath_at(pos: Vector2):
	var path_dup = AreaX.duplicate()
	add_child(path_dup)
	path_dup.global_position = pos
	path_dup.show()

func spawn_verticlepath_at(pos: Vector2):
	var path_dup = AreaY.duplicate()
	add_child(path_dup)
	path_dup.global_position = pos
	path_dup.show()
	
# --- THE GENERATION LOGIC ---

func spawn_horizontaldoor_at(pos: Vector2):
	var door_dup = XDoorSprite.duplicate()
	add_child(door_dup)
	var marker_offset = XDoorSprite.get_node("Marker2D").position
	door_dup.global_position = pos - marker_offset
	door_dup.show()

func spawn_verticledoor_at(pos: Vector2):
	var door_dup = YDoorSprite.duplicate()
	add_child(door_dup)
	var marker_offset = YDoorSprite.get_node("Marker2D").position
	door_dup.global_position = pos - marker_offset
	door_dup.show()


func _on_area_y_door_body_entered(body: Node2D) -> void:
	# Only trigger if the Player walks into it
	if body == player:
		print("Player hit a door! Generating new floor...")
		var floor_dup = Floor.duplicate()
		get_parent().add_child(floor_dup) # Add to world, not the current room
		floor_dup.global_position = player.global_position + Vector2(0, 200)


func _on_area_x_door_body_entered(body: Node2D) -> void:
	# Only trigger if the Player walks into it
	if body == player:
		print("Player hit a door! Generating new floor...")
		var floor_dup = Floor.duplicate()
		get_parent().add_child(floor_dup) # Add to world, not the current room
		floor_dup.global_position = player.global_position + Vector2(200, 0)
