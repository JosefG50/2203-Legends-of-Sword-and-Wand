package godot

// KEEP THIS ONE - This is the 4.5.1 standard
import godot.api.GD
import godot.api.Node
import godot.annotation.RegisterClass
import godot.annotation.RegisterFunction

// DELETE THIS ONE - It causes the "Conflicting Import" or "Ambiguous Reference" error
// import godot.global.GD 

@RegisterClass
class MainScript : Node() {
    
    @RegisterFunction
    override fun _ready() {
        // Now the compiler knows exactly which GD to use
        GD.print("4.5.1 Build Successful")
    }
}