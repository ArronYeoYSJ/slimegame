#version 330

in vec4 passColour;
out vec4 outColour;

uniform sampler2D texture1;
//in vec2 TexCoords;

//uniform sampler2D texture1;

void main()
{

    // vec2 vFade = vec2(1,1) - 2*abs(passST - vec2(0.2,0.2));
    // float fade = min(vFade.x, vFade.y);
    outColour = passColour; 
}
