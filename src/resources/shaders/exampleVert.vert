#version 330
layout(location = 0) in vec4 position;
layout(location = 1) in vec4 colour;
layout(location = 2) in vec2 ST;
layout(location = 3) in vec3 normal;


out vec4 passColour;
out vec2 passST;
out vec3 passNormalMV;
out vec3 passPositionMV;
out float[10] passLightingData;
out vec3 passLightPosMV;
out vec4 lightPVM;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;
uniform mat4 lightView;
uniform mat4 lightProjection;
uniform float[10] lightingData;

void main() {
    gl_Position =  projection * view * model * position;
    passColour = colour;
    passST = ST;
    passNormalMV = normalize(mat4(model) * vec4(normal, 0.0)).xyz;
    passPositionMV = (view * model * position).xyz;
    passLightingData = lightingData;
    vec3 lightPos = vec3(lightingData[0], lightingData[1], lightingData[2]);
    passLightPosMV = passPositionMV + lightPos;

    lightPVM = lightProjection * lightView * vec4(passLightPosMV, 1.0);
}
