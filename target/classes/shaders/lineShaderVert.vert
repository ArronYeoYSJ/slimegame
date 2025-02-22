#version 330

layout(location = 0) in vec4 position;

out vec4 passColour;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

float r = 0.0;
float g = 1.0;
float b = 0.0;
float a = 1.0;

void main() {
    gl_Position = projection * view * model * position;
    
    passColour = vec4(r, g, b, a);
}