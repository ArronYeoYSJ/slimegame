#version 330

in vec4 passColour;
in vec2 passST;
in vec3 passNormalMV;
in vec3 passPositionMV;
in float[10] passLightingData;
in vec3 passLightPosMV;
out vec4 outColour;

uniform sampler2D texture1;
uniform vec4 minLight;
uniform int numLights;
uniform vec4 baseColour;
uniform float alpha;
//in vec2 TexCoords;

//uniform sampler2D texture1;
vec3 calcLighting(float[10] passLightingData){

    vec3 lPosition = passLightPosMV;
    vec3 lColour = vec3(passLightingData[3], passLightingData[4], passLightingData[5]);
    //assuming intensity is between 0 and 1
    float intensity = passLightingData[6];
    float range = passLightingData[7];

    vec3 relativePosVec = lPosition.xyz - passPositionMV;

    float dist = length(relativePosVec);
    float attenuation = 1.0 - clamp(dist / range, 0.0, 1.0);

    vec3 lightDir = normalize(relativePosVec);

    float dotProd = max(dot(passNormalMV, lightDir), 0.0);
    float product = dotProd * intensity * attenuation;

    vec3 output = vec3(lColour.rgb * product);

    vec3 result = clamp(output, minLight.xyz, vec3(1.0, 1.0, 1.0));
    
    return result;
}

void main()
{

    vec3 lighting = calcLighting(passLightingData);
    vec4 texColour = texture(texture1, passST);
    outColour = vec4(baseColour.rgb * texColour.rgb * lighting, 1.0);
}



