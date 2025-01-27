#version 330
layout(location = 0) in vec4 position;
layout(location = 1) in vec4 colour;
layout(location = 2) in vec2 ST;

out vec4 passColour;
out vec2 passST;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

void main() {
    gl_Position = projection * view * model * position;
    passColour = colour;
    passST = ST;
}
