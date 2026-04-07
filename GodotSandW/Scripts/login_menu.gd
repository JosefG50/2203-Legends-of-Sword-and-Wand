extends Control

@onready var http_request = $HTTPRequest
@onready var username_input = $PanelContainer/VBoxContainer/LineEdit_Username
@onready var password_input = $PanelContainer/VBoxContainer/LineEdit_Password
@onready var status_label = $Label_Status
@onready var submitbtn = $PanelContainer/VBoxContainer/MarginContainer/Submit

var login_url = "http://localhost:8080/auth/login"
var register_url = "http://localhost:8080/auth/register"


var current_mode = "login" 

func _ready():
	submitbtn.pressed.connect(_on_submit_pressed)
	http_request.request_completed.connect(_on_http_request_request_completed)

func _on_submit_pressed() -> void:
	var user = username_input.text
	var pass_val = password_input.text
	
	if user == "" or pass_val == "":
		status_label.text = "Please enter both fields."
		return

	var auth_data = {"username": user, "password": pass_val}
	var json_query = JSON.stringify(auth_data)
	var headers = ["Content-Type: application/json"]
	var target_url = login_url if current_mode == "login" else register_url
	http_request.request(target_url, headers, HTTPClient.METHOD_POST, json_query)
	status_label.text = "Connecting to Server..."

func _on_http_request_request_completed(_result, response_code, _headers, body):
	var response_text = body.get_string_from_utf8()
	
	if response_code == 200:
		status_label.text = "Success!"
		if current_mode == "login":
			get_tree().change_scene_to_file("res://choose_profile.tscn")
	else:
		status_label.text = response_text

func _on_go_back_pressed() -> void:
	get_tree().change_scene_to_file("res://Menu.tscn")
