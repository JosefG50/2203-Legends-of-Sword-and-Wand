extends Control

@onready var http_request = $HTTPRequest
@onready var username_input = $PanelContainer/VBoxContainer/LineEdit_Username
@onready var password_input = $PanelContainer/VBoxContainer/LineEdit_Password
@onready var email_input = $PanelContainer/VBoxContainer/LineEdit_Email
@onready var status_label = $Label_Status
@onready var submitbtn = $PanelContainer/VBoxContainer/MarginContainer/Submit

# This matches the @PostMapping("/register") in your AuthController
var register_url = "http://localhost:8080/auth/register"

func _ready() -> void:
	# Connect the signals
	submitbtn.pressed.connect(_on_submit_pressed)
	http_request.request_completed.connect(_on_http_request_completed)

func _on_submit_pressed() -> void:
	var user = username_input.text
	var pass_val = password_input.text
	
	if user == "" or pass_val == "":
		status_label.text = "Please enter a username and password."
		return

	# Create the data dictionary to send to Java
	var register_data = {
		"username": user,
		"password": pass_val
	}
	
	var json_query = JSON.stringify(register_data)
	var headers = ["Content-Type: application/json"]
	
	# Send the request
	http_request.request(register_url, headers, HTTPClient.METHOD_POST, json_query)
	status_label.text = "Creating account..."

func _on_http_request_completed(result, response_code, headers, body):
	var response_text = body.get_string_from_utf8()
	
	if response_code == 200:
		status_label.text = "Account Created! Redirecting..."
		# Wait 1.5 seconds so they can see the success message
		await get_tree().create_timer(1.5).timeout
		get_tree().change_scene_to_file("res://login_menu.tscn")
	else:
		# This will show "Error: That username already exists" from your Java code
		status_label.text = response_text

func _on_go_back_pressed() -> void:
	get_tree().change_scene_to_file("res://Menu.tscn")
