extends Node2D

@export_group("Speeds")
@export var scroll_speed: float = 150.0
@export var rotation_speed: float = 20.0  # Degrees per second

@export_group("Orbit")
@export var orbit_radius: float = 350.0
@export var start_rotation_deg: float = 0.0

@onready var day_sky = $TextureRectSun
@onready var night_sky = $TextureRectMoon
@onready var pivot = $Pivot
@onready var sun = $Pivot/Sprite2D
@onready var moon = $Pivot/Sprite2D2

var texture_width: float = 0.0
var total_dist: float = 0.0
var screen_size: Vector2

func _ready():
	await get_tree().process_frame
	screen_size = get_viewport_rect().size
	
	# 1. Setup Texture Width & Tiling
	if day_sky.texture:
		texture_width = day_sky.texture.get_size().x * day_sky.scale.x
		# Make them wide enough to cover screen + one full loop
		day_sky.size = Vector2(texture_width + screen_size.x, screen_size.y)
		night_sky.size = day_sky.size
		day_sky.stretch_mode = TextureRect.STRETCH_TILE
		night_sky.stretch_mode = TextureRect.STRETCH_TILE

	# 2. Reset Positions to zero so the script handles placement
	day_sky.position = Vector2.ZERO
	night_sky.position = Vector2.ZERO
	
	# 3. Center the Pivot and setup Sun/Moon
	pivot.global_position = screen_size / 2
	pivot.rotation_degrees = start_rotation_deg
	
	sun.position = Vector2(0, -orbit_radius)
	moon.position = Vector2(0, orbit_radius)

func _process(delta):
	# --- 1. THE SKY SCROLL ---
	total_dist += scroll_speed * delta
	var offset = fmod(total_dist, texture_width)
	
	# We only move the sky, NOT the parent Background node
	day_sky.position.x = -offset
	night_sky.position.x = -offset
	
	# --- 2. THE ROTATION ---
	pivot.rotation_degrees += rotation_speed * delta
	
	# --- 3. THE ALPHA FADE (BASED ON HEIGHT) ---
	# We check how high the sun is relative to the center of the screen
	# If sun.global_position.y is low (negative Y is UP), it's day.
	var center_y = screen_size.y / 2
	var sun_y = sun.global_position.y
	
	# This line calculates alpha: 1.0 when sun is at the top, 0.0 when at bottom
	var day_alpha = remap(sun_y, center_y + orbit_radius, center_y - orbit_radius, 0.0, 1.0)
	day_alpha = clamp(day_alpha, 0.0, 1.0)
	
	day_sky.modulate.a = day_alpha
	night_sky.modulate.a = 1.0 - day_alpha
	
	# --- 4. KEEP SPRITES UPRIGHT ---
	sun.global_rotation = 0
	moon.global_rotation = 0
