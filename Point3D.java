package com.company;

public class Point3D {
    private double xCoord;
    private double yCoord;
    private double zCoord;
    public Point3D(double x,double y,double z){
        xCoord = x;
        yCoord = y;
        zCoord = z;
    }
    public Point3D(){
        this(0,0,0);
    }
    public double getX(){return xCoord;}
    public double getY(){return yCoord;}
    public double getZ(){return zCoord;}
    public void setX(double val){
        xCoord = val;
    }
    public void setY(double val){
        yCoord = val;
    }
    public void setZ(double val){
        zCoord = val;
    }
    public boolean changeL(Point3D A2){
        if(getX() != A2.getX()) return false;
        if(getY() != A2.getY()) return false;
        if(getZ() != A2.getZ()) return false;
        return true;
    }
    public double distanceToX(Point3D A1){
        return getX() - A1.getX();
    }
    public double distanceToY(Point3D A1){
        return getY() - A1.getY();
    }
    public double distanceToZ(Point3D A1){
        return getZ() - A1.getZ();
    }
}
