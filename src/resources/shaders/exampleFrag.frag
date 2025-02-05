#version 330

in vec4 passColour;
in vec2 passST;
in vec3 passNormalMV;
in vec3 passPositionMV;
out vec4 outColour;

uniform sampler2D texture1;
//in vec2 TexCoords;

//uniform sampler2D texture1;

void main()
{

    // vec2 vFade = vec2(1,1) - 2*abs(passST - vec2(0.2,0.2));
    // float fade = min(vFade.x, vFade.y);
    float xshadow = 0.3 * (cos(passNormalMV.x) + cos(passNormalMV.z));
    float yshadow = 0.5 * passNormalMV.y;
    float shadow = 1.0 - xshadow + yshadow;

    outColour = texture(texture1, passST) ;/* vec4(shadow, shadow, shadow, 1.0); */
}
