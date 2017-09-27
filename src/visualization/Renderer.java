package visualization;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Optional;

import model.Part;
import model.Solid;
import transforms.Col;
import transforms.Mat4;
import transforms.Point3D;
import transforms.Vec3D;
import model.TopologyType;
import model.VertexBase;
import rasterop.ZBuffer;

public class Renderer {
    private int colorIterator;
    private Graphics g;
    private Mat4 model, view, projection, cT;
    private BufferedImage img;
    private ZBuffer zb;
    private TriangleRasterizer tr;
    private boolean drawAxis = false;


    public Renderer(BufferedImage img) {
        this.img = img;
        this.g = img.getGraphics();
        zb = new ZBuffer(img.getWidth(), img.getHeight(), img);
        tr = new TriangleRasterizer(zb);
    }

    public void draw(Solid solid, boolean wiredModel) {
        colorIterator = 0;

        if (solid.getPart(0).getTopology() == TopologyType.AXIS) {
            cT = view.mul(projection);
        } else {
            cT = model.mul(view.mul(projection));

        }

        for (Part p : solid.getParts()) {
            if (p.getTopology() == TopologyType.LINETOPOLOGY && wiredModel) {
                renderLineList(p, solid);
            } else if (p.getTopology() == TopologyType.AXIS) {
                drawAxis = true;
                renderLineList(p, solid);
            } else if (p.getTopology() == TopologyType.TRIANGLETOPOLOGY && !wiredModel) {
                renderTriangleList(p, solid);
            }
        }
    }

    private void renderTriangleList(Part p, Solid solid) {
        if (p.getStartIndex() < 0 || p.getStartIndex() + p.getCount() > solid.getIndices().size() || p.getCount() % 3 == 0) {
            System.err.println("Illegal argument");
            return;
        }
        for (int i = p.getStartIndex(); i < p.getStartIndex() + p.getCount(); i += 3) {
            VertexBase v1 = (VertexBase) solid.getVertices().get(solid.getIndices().get(i));
            v1.setColor(solid.getColors().get(colorIterator));

            VertexBase v2 = (VertexBase) solid.getVertices().get(solid.getIndices().get(i + 1));
            v2.setColor(solid.getColors().get(colorIterator));

            VertexBase v3 = (VertexBase) solid.getVertices().get(solid.getIndices().get(i + 2));
            v3.setColor(solid.getColors().get(colorIterator));

            transformTriangle(v1, v2, v3);
        }
    }

    private void transformTriangle(VertexBase v1, VertexBase v2, VertexBase v3) {
        double wMin = 0.0001;

        //transformace
        VertexBase tv1 = v1.mul(cT);
        tv1.setColor(v1.getColor());

        VertexBase tv2 = v2.mul(cT);
        tv2.setColor(v2.getColor());

        VertexBase tv3 = v3.mul(cT);
        tv3.setColor(v3.getColor());

        //serazeni tp1.w >= tp2.w >= tp3.w
        if (tv1.getW() < tv2.getW()) {
            VertexBase pom = tv1;
            tv1 = tv2;
            tv2 = pom;
        }

        if (tv2.getW() < tv3.getW()) {
            VertexBase pom = tv2;
            tv2 = tv3;
            tv3 = pom;
        }
        if (tv1.getW() < tv2.getW()) {
            VertexBase pom = tv1;
            tv1 = tv2;
            tv2 = pom;
        }

        if (tv3.getW() >= wMin) {
            dehomogTriangle(tv1, tv2, tv3);
            return;
        }
        if (tv2.getW() >= wMin) {
            double ta = (wMin - tv3.getW()) / (tv1.getW() - tv3.getW());
            double tb = (wMin - tv3.getW()) / (tv2.getW() - tv3.getW());
            VertexBase va = tv3.mul(1 - ta).add(tv1.mul(ta));
            VertexBase vb = tv3.mul(1 - tb).add(tv2.mul(tb));
            dehomogTriangle(tv1, tv2, va);
            dehomogTriangle(tv1, va, vb);
            return;
        }
        if (tv1.getW() >= wMin) {
            double ta = ((wMin - tv2.getW()) / (tv1.getW() - tv2.getW()));
            double tb = ((wMin - tv3.getW()) / (tv1.getW() - tv3.getW()));
            VertexBase va = tv2.mul(1 - ta).add(tv1.mul(ta));
            VertexBase vb = tv3.mul(1 - tb).add(tv1.mul(tb));
            dehomogTriangle(tv1, va, vb);
        }
    }

    private void dehomogTriangle(VertexBase tv1, VertexBase tv2, VertexBase tv3) {
        Point3D v1 = tv1.getPosition().mul(1 / tv1.getPosition().getW());
        Point3D v2 = tv2.getPosition().mul(1 / tv2.getPosition().getW());
        Point3D v3 = tv3.getPosition().mul(1 / tv3.getPosition().getW());

        Col cv1 = tv1.getColor();
        Col cv2 = tv2.getColor();
        Col cv3 = tv3.getColor();

        drawTriangle(v1, v2, v3, cv1, cv2, cv3);
    }

    private void drawTriangle(Point3D v1, Point3D v2, Point3D v3, Col cv1, Col cv2, Col cv3) {
        double v1x = v1.getX();
        double v1y = v1.getY();
        double v1z = v1.getZ();
        double v2x = v2.getX();
        double v2y = v2.getY();
        double v2z = v2.getZ();
        double v3x = v3.getX();
        double v3y = v3.getY();
        double v3z = v3.getZ();

        if ((Math.min(Math.min(v1x, v2x), v3x) > 1.0f) || (Math.max(Math.max(v1x, v2x), v3x) <= -1.0f))
            if ((Math.min(Math.min(v1y, v2y), v3y) > 1.0f) || (Math.max(Math.max(v1y, v2y), v3y) <= -1.0f))
                if ((Math.min(Math.min(v1z, v2z), v3z) > 1.0f) || (Math.max(Math.max(v1z, v2z), v3z) <= 0))
                    return;

        double x1 = (v1x + 1) * 0.5 * (img.getWidth() - 1);
        double y1 = (-v1y + 1) * 0.5 * (img.getHeight() - 1);
        double x2 = (v2x + 1) * 0.5 * (img.getWidth() - 1);
        double y2 = (-v2y + 1) * 0.5 * (img.getHeight() - 1);
        double x3 = (v3x + 1) * 0.5 * (img.getWidth() - 1);
        double y3 = (-v3y + 1) * 0.5 * (img.getHeight() - 1);
        tr.draw(x1, y1, v1z, x2, y2, v2z, x3, y3, v3z, cv1, cv2, cv3);
        colorIterator++;
    }

    //Rendering of lines-----------------------------------------------------------------------------------------------------------
    private void renderLineList(Part p, Solid solid) {
        if (p.getStartIndex() < 0 || p.getStartIndex() + p.getCount() > solid.getIndices().size() || p.getCount() % 2 == 0) {
            System.out.println(p.getStartIndex());
            System.out.println(p.getCount());
            System.out.println(solid.getIndices().size());

            System.err.println("Illegal argument");
            return;
        }
        for (int i = p.getStartIndex(); i < p.getStartIndex() + p.getCount(); i += 2) {
            Point3D p1 = solid.getVertices().get(solid.getIndices().get(i)).getPosition();
            Point3D p2 = solid.getVertices().get(solid.getIndices().get(i + 1)).getPosition();
            transformLine(p1, p2);
        }
    }

    private void transformLine(Point3D p1, Point3D p2) {
        Point3D tp1 = p1.mul(cT);
        Point3D tp2 = p2.mul(cT);
        if (tp1.getW() <= 0 || tp2.getW() <= 0) return;
        Optional<Vec3D> dp1 = tp1.dehomog();
        Optional<Vec3D> dp2 = tp2.dehomog();
        dp1.ifPresent((Vec3D v1) -> dp2.ifPresent(v2 -> drawLine(v1, v2)));
    }

    private void drawLine(Vec3D p1, Vec3D p2) {
        int x1 = (int) ((p1.getX() + 1) * 0.5 * (img.getWidth() - 1));
        int y1 = (int) ((-p1.getY() + 1) * 0.5 * (img.getHeight() - 1));
        int x2 = (int) ((p2.getX() + 1) * 0.5 * (img.getWidth() - 1));
        int y2 = (int) ((-p2.getY() + 1) * 0.5 * (img.getHeight() - 1));
        if (drawAxis) {
            if (colorIterator == 0) g.setColor(Color.BLUE);
            if (colorIterator == 1) g.setColor(Color.RED);
            if (colorIterator == 2) g.setColor(Color.GREEN);
            g.drawLine(x1, y1, x2, y2);
            colorIterator++;
            if (colorIterator == 3) drawAxis = false;
        } else {
            //img.getGraphics().drawLine(x1, y1, x2, y2);
            g.setColor(Color.WHITE);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    public void draw(List<Solid> solidList, boolean wiredonly) {
        zb.clear(img.getWidth(), img.getHeight(), new Col(0x2f2f2f));

        for (Solid s : solidList) {
            draw(s, wiredonly);
        }
    }

    public Mat4 getModel() {
        return model;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public Mat4 getView() {
        return view;
    }

    public void setView(Mat4 view) {
        this.view = view;
    }

    public Mat4 getProjection() {
        return projection;
    }

    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }


}
