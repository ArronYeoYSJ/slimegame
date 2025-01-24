#version 330
layout(location = 0) in vec4 position;
layout(location = 1) in vec4 colour;
layout(location = 2) in vec2 ST;

out vec4 passColour;
out vec2 passST;

uniform vec4 offset;
uniform vec4 scale;

void main() {
    gl_Position = vec4 (position.x * scale.x, position.y * scale.y, position.z * scale.z, position.w);// + offset;
    passColour = colour;
    passST = ST;
}
