extends Node2D

@onready var TopDoorMark = $Template/TopDoor
@onready var BotDoorMark = $Template/BotDoor
@onready var LeftDoorMark = $Template/LeftDoor
@onready var RightDoorMark = $Template/RightDoor
@onready var XDoorSprite = $Template/XDoorSprite
@onready var YDoorSprite = $Template/YDoorSprite
@onready var AreaX = $Template/AreaXDoor
@onready var AreaY = $Template/AreaYDoor
@onready var Floor = $Template/Floor
@onready var walls = $Template/AllOpen


static var spawned_rooms: Dictionary = {} # Stores Vector2: bool
var door_offsets: Dictionary = {}

func _ready() -> void:
	call_deferred("init_rooms")  # rename here

func init_rooms() -> void:       # and here
	var origin = Floor.global_position
	door_offsets = {
		"top":   TopDoorMark.global_position   - origin,
		"bot":   BotDoorMark.global_position   - origin,
		"left":  LeftDoorMark.global_position  - origin,
		"right": RightDoorMark.global_position - origin,
	}
	spawnstartingroom($Marker2D.global_position, "top")

func spawnstartingroom(pos: Vector2, entrance: String) -> void:
	# Mark this coordinate as occupied immediately
	spawned_rooms[pos] = true
	# Spawn floor and walls
	var floor_dup = Floor.duplicate()
	var wall_dup  = walls.duplicate()
	get_parent().add_child(floor_dup)
	get_parent().add_child(wall_dup)
	floor_dup.global_position = pos
	wall_dup.global_position  = pos
	floor_dup.show()
	wall_dup.show()

	# Pick random exits, never on the entrance side
	var all_dirs = ["top", "bot", "left", "right"]
	all_dirs.erase(entrance)
	all_dirs.shuffle()
	var exits = all_dirs.slice(0, randi_range(1, 3))

	# Entrance = door sprite only (sealed, no going back)
	# Exits    = open area2d only (no sprite blocking the path)
	# Closed   = door sprite (wall with no passage)

	for dir in exits:
		_place_door_area(dir, pos)
	for dir in all_dirs:
		if not exits.has(dir):
			_place_door_sprite(dir, pos)

func _place_door_sprite(dir: String, room_pos: Vector2) -> void:
	var is_vertical = dir in ["top", "bot"]
	var template    = YDoorSprite if is_vertical else XDoorSprite
	var door_dup    = template.duplicate()
	get_parent().add_child(door_dup)
	door_dup.global_position = (room_pos + door_offsets[dir]) - template.get_node("Marker2D").position
	door_dup.show()

func _place_door_area(dir: String, room_pos: Vector2) -> void:
	var is_vertical = dir in ["top", "bot"]
	var area_dup = (AreaY if is_vertical else AreaX).duplicate()
	get_parent().add_child(area_dup)
	
	area_dup.global_position = room_pos + door_offsets[dir]
	area_dup.show()
	
	area_dup.body_entered.connect(func(body):
		if body.scene_file_path != "res://player.tscn":
			return
		
		area_dup.queue_free()
		_on_exit_triggered(dir, room_pos)
	)

func _on_exit_triggered(dir: String, from_room_pos: Vector2) -> void:
	var opposite = {"top": "bot", "bot": "top", "left": "right", "right": "left"}
	var offsets = {"top": Vector2(-8, -64), "bot": Vector2(-8, 48), "left": Vector2(-88, 0), "right": Vector2(88, 0)}
	
	var new_pos = from_room_pos + door_offsets[dir] + offsets[dir]
	
	# --- THE FIX: COORDINATE SNAPPING & CHECK ---
	# We round the position to avoid tiny floating point errors (e.g. 599.99 vs 600)
	var snapped_pos = new_pos.snapped(Vector2(8, 8)) 

	if spawned_rooms.has(snapped_pos):
		print("Room already exists at ", snapped_pos, " - Skipping spawn.")
		# Instead of spawning, you could just place an open door here to connect them
		return 

	spawnstartingroom(snapped_pos, opposite[dir])

# Satisfies the old Inspector signal connections — does nothing
func _on_area_y_door_body_entered(_body: Node2D) -> void:
	pass

func _on_area_x_door_body_entered(_body: Node2D) -> void:
	pass
