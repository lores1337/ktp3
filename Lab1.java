package com.company;

import java.util.Scanner;

public class Lab1 {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        double arx;
        double ary;
        double arz;
        Point3D [] pd = new Point3D[3];
        for(int i=0;i<3;i++){
            System.out.print("Введите координату x для " + (i+1) + " точки: \r"); arx = s.nextDouble();
            System.out.print("Введите координату y для " + (i+1) + " точки: \r"); ary = s.nextDouble();
            System.out.print("Введите координату z для " + (i+1) + " точки: \r"); arz = s.nextDouble();
            pd[i] = new Point3D(arx,ary,arz);
            if(i != 2) System.out.println();}
        if(pd[0].changeL(pd[1]) || pd[0].changeL(pd[2]) || pd[1].changeL(pd[2])) System.out.println("Присутсвуют одиннаковые точки");
        else System.out.println("Площадь треугольника равна: " + computeArea(pd[0],pd[1],pd[2]));
    }
    public static double computeArea(Point3D A1,Point3D A2,Point3D A3){
        Point3D P1 = new Point3D();
        P1.setX(A2.getX() - A1.getX());
        P1.setY(A2.getY() - A1.getY());
        P1.setZ(A2.getZ() - A1.getZ());
        Point3D P2 = new Point3D();
        P2.setX(A3.getX() - A1.getX());
        P2.setY(A3.getY() - A1.getY());
        P2.setZ(A3.getZ() - A1.getZ());
        Point3D P3 = new Point3D();
        P3.setX(P1.getY()*P2.getZ() - P1.getZ()*P2.getY());
        P3.setY(P1.getZ()*P2.getX() - P1.getX()*P2.getZ());
        P3.setZ(P1.getX()*P2.getY() - P1.getY()*P2.getX());
        return (Math.sqrt(P3.getX()*P3.getX() + P3.getY()*P3.getY() + P3.getZ()*P3.getZ()) / 2);
    }
}
