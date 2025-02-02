#version 330
layout(location = 0) in vec4 position;
layout(location = 1) in vec4 colour;
layout(location = 2) in vec2 ST;
layout(location = 3) in vec3 normal;

out vec4 passColour;
out vec2 passST;
out vec3 passNormalMV;
out vec3 passPositionMV;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

void main() {
    gl_Position =  projection * view * model * position;
    passColour = colour;
    passST = ST;
    passNormalMV = normalize(mat4(view * model) * vec4(normal, 0.0)).xyz;
    passPositionMV = (view * model * position).xyz;
}
