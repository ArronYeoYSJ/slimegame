#version 330
layout(location = 0) in vec4 position;
layout(location = 1) in vec4 colour;
layout(location = 2) in vec2 ST;

out vec4 passColour;
out vec2 passST;


void main() {
    gl_Position = position;// + offset;
    passColour = colour;
    passST = ST;
}
