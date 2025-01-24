package com.arronyeoman.maths;

public class Curves {
    
    public void test (int t){
        Vertex[] testVertexes = new Vertex[]{
            new Vertex(0, 0),
            new Vertex(1, 1),
            new Vertex(0, 2),
            new Vertex(2, 3)
        };

        System.out.println(bezierCalc(t, testVertexes).toString());
    }


    public Vertex[] bezierCalc (int numPoints, Vertex[] controlPoints) {
        float[] pointArray = genWeights(numPoints);
        Vertex[] outputPoints = new Vertex[numPoints];
        float[] xPoints = new float[controlPoints.length];
        float[] yPoints = new float[controlPoints.length];

        for (int j=0 ; j < controlPoints.length -1; j++) {
            xPoints[j] = controlPoints[j].x;
            yPoints[j] = controlPoints[j].y;
        }
    
        if (controlPoints.length == 4)
        {
            for (int i = 0; i < numPoints -1; i++)
            {
                outputPoints[i] = new Vertex(cubicBezier(xPoints, pointArray[i]), cubicBezier(yPoints, pointArray[i]));
            }
        }
        else if (controlPoints.length == 3)
        {
            for (int i = 0; i < numPoints; i++)
            {
                outputPoints[i] = new Vertex(quadraticBezier(xPoints, pointArray[i]), quadraticBezier(yPoints, pointArray[i]));
            }
        }
        else if (controlPoints.length == 2)
        {
            for (int i = 0; i < numPoints; i++)
            {
                outputPoints[i] = new Vertex(linearInterpolate(xPoints, pointArray[i]), linearInterpolate(yPoints, pointArray[i]));
            }
        }

        return outputPoints;
    }

    private float[] genWeights(int numWeights) {
        float[] points = new float[numWeights];
        for (int k = 0; k < numWeights; k++){
            points[k]= k / numWeights;
        }
        return points;
    }

    private float linearInterpolate (float[] w, float t) {
        return w[0] + t * (w[1] - w[0]);
    }

    private float quadraticBezier (float[] w, float t) {
        float tSquared = t * t;
        float oneMinusT = 1 - t;
        float oneMinusTSquared = oneMinusT * oneMinusT;

        return w[0]*(oneMinusTSquared) + (w[1] * (2 * oneMinusT * t)) + (w[2] * (tSquared));
    }

    private float cubicBezier (float[] w, float t) {
        float tSquared = t * t;
        float tCubed = tSquared * t;
        float oneMinusT = 1 - t;
        float oneMinusTSquared = oneMinusT * oneMinusT;
        float oneMinusTCubed = oneMinusTSquared * oneMinusT;

        return w[0] * oneMinusTCubed + (w[1] * 3 * oneMinusTSquared * t) + (w[2] * 3 * oneMinusT * tSquared) + (w[3] * tCubed);
    }
}
