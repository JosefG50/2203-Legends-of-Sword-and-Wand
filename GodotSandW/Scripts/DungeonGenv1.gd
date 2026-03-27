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
@onready var player = $Player

@onready var Template =$Template

func _ready() -> void:
	#example functions
	#spawn_verticledoor_at(TopDoorMark.global_position)
	#spawn_verticlepath_at(TopDoorMark.global_position)
	#spawn_verticlepath_at(BotDoorMark.global_position)
	#spawn_horizontalpath_at(LeftDoorMark.global_position)
	#spawn_horizontalpath_at(RightDoorMark.global_position)
	#spawn template dup
	call_deferred("spawnstartingroom")

func spawnstartingroom():
	var floor_dup = Floor.duplicate()
	var wall_dup = walls.duplicate()
	get_parent().add_child(floor_dup)
	get_parent().add_child(wall_dup)
	floor_dup.global_position = Vector2(600, 50)
	wall_dup.global_position = Vector2(600,50)
	floor_dup.show()
	wall_dup.show()

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
		var wall_dup = walls.duplicate()
		get_parent().add_child(floor_dup)
		get_parent().add_child(wall_dup)
		var floor_center_y = Floor.global_position.y
		var wall_center_y = walls.global_position.y
		if player.global_position.y > floor_center_y:
			print("Hit BOTTOM door")

			floor_dup.global_position = BotDoorMark.global_position + Vector2(-8, 48)
			wall_dup.global_position = BotDoorMark.global_position + Vector2(-8, 48)
		else:
			print("Hit TOP door")
			floor_dup.global_position = TopDoorMark.global_position + Vector2(-8, -64)
			wall_dup.global_position = TopDoorMark.global_position + Vector2(-8, -64)

func _on_area_x_door_body_entered(body: Node2D) -> void:
	if body == player:
		var floor_dup = Floor.duplicate()
		var wall_dup = walls.duplicate()
		get_parent().add_child(floor_dup)
		get_parent().add_child(wall_dup)
		var floor_center_x = Floor.global_position.x
		if player.global_position.x < floor_center_x:
			print("Player is on Left half: Spawning New Floor to the Left")
			floor_dup.global_position = LeftDoorMark.global_position+ Vector2(-88, 0)
			wall_dup.global_position = LeftDoorMark.global_position + Vector2(-104, 0)
		else:
			print("Player is on Right half: Spawning New Floor to the Right")
			floor_dup.global_position = RightDoorMark.global_position+ Vector2(88, 0)
			wall_dup.global_position = RightDoorMark.global_position + Vector2(88, 0)
