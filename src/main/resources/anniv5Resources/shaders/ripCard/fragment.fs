#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

in vec2 v_texCoord;

varying LOWP vec4 v_color;

uniform sampler2D u_texture;
uniform float u_time;
uniform vec2 u_screenSize;

vec2 toScreenSpace(vec2 coord) {
    return coord * u_screenSize;
}
float toScreenSpace(float coord) {
    return coord * u_screenSize.x;
}
void main() {
	vec4 color = texture(u_texture, v_texCoord);
	vec4 transparentColor = vec4(1.0, 1.0, 1.0, 0.0);
	vec4 finalColor;
	vec2 displacement = normalize(toScreenSpace(v_texCoord) - (u_time * 100.0);
	if(v_texCoord.y < 0.5) {
	    finalColor = mix(color, transparentColor, 1.0);
	} else {
	    finalColor = v_color * texture2D(u_texture, v_texCoord - displacement);
	}

	gl_FragColor = finalColor;
}
