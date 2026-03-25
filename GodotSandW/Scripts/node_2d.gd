extends Node

var url = "http://localhost:8080/battle/state"

func _ready():
	$HTTPRequest.request_completed.connect(_on_request_completed)

	var error = $HTTPRequest.request(url)
	if error != OK:
		print("An error occurred in the HTTP request.")

func _on_request_completed(result, response_code, headers, body):
	var response_text = body.get_string_from_utf8()
	print("Raw Response from Server: ", response_text) 
	
	var json = JSON.parse_string(response_text)
	
	if json == null:
		print("Error: Could not parse JSON. Is the response empty?")
		return


	if json.has("currentUnitName"):
		print("The Hero's Name is: ", json["currentUnitName"])
	else:
		print("Key 'currentUnitName' not found. Available keys: ", json.keys())
