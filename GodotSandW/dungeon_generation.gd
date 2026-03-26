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
	if body == player:
		var floor_dup = Floor.duplicate()
		get_parent().add_child(floor_dup)
		
		# If Player's Y is lower (larger number) than the Marker, they hit BOTTOM
		# If Player's Y is higher (smaller number) than the Marker, they hit TOP
		if player.global_position.y > global_position.y:
			print("Hit BOTTOM door")
			floor_dup.global_position = BotDoorMark.global_position + Vector2(0, 200)
		else:
			print("Hit TOP door")
			# You'll need to offset the new floor by its own height 
			# so its bottom matches your top
			floor_dup.global_position = TopDoorMark.global_position - Vector2(0, 0) # Example height

func _on_area_x_door_body_entered(body: Node2D) -> void:
	if body == player:
		var floor_dup = Floor.duplicate()
		get_parent().add_child(floor_dup)
		
		# If Player's X is further right than the Marker, they hit RIGHT
		if player.global_position.x > global_position.x:
			print("Hit RIGHT door")
			floor_dup.global_position = RightDoorMark.global_position + Vector2(0, 0)
		else:
			print("Hit LEFT door")
			# Offset by floor width so its right side matches your left
			floor_dup.global_position = LeftDoorMark.global_position - Vector2(0, 0) # Example width
