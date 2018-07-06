package models;

import enums.*;
import models.building.Building;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class PathFinder {
    private int inRadiusPath = 2;
    private int radius;
    private int limit;
    HashMap<Coordinate, Building> map;
    private HashMap<Integer, Node> openedList = new HashMap<>();
    private HashMap<Integer, Building> unPassableList = new HashMap<>();
    private HashMap<Integer, Node> closedList = new HashMap<>();
    private ArrayList<Building> inWay = new ArrayList<>();
    private Node startNode;
    private Node aimNode;
    private Coordinate aimCoordination;
    private ArrayList<Coordinate> pathToAim;

    public PathFinder(int radius) {
        this.radius = radius;
    }

    public void setData(int aim, HashMap<Coordinate, Building> map, int limit, int currentLoc, SoldierMoveType moveType) {
        pathToAim = new ArrayList<>();
        if (moveType == SoldierMoveType.GROUND)
            for (Building building :
                    map.values()) {
                if (building == null || building.getCoordinate().getY() == Coordinate.getCoordination(aim).getY() &&
                        building.getCoordinate().getX() == Coordinate.getCoordination(aim).getX()) {
                    continue;
                }
                unPassableList.put(building.getCoordinate().toInt(), building);
            }

        this.limit = limit;
        startNode = new Node(currentLoc);
        openedList.put(currentLoc, startNode);
        aimNode = new Node(aim);
        aimCoordination = Coordinate.getCoordination(aim);
    }

    public ArrayList<Building> getInWay() {
        return inWay;
    }

    public Coordinate getNextStep() {
        if (pathToAim.size() < inRadiusPath)
            return null;
        pathToAim.remove(pathToAim.size() - 1);
        return pathToAim.get(pathToAim.size() - 1);
    }

    public void findPath() {
        int minCost = Integer.MAX_VALUE;
        Node bestNode = new Node(-1);
        for (Node node :
                openedList.values()) {
            if (node.getCostFromStart() + distanceCalculator(Coordinate.getCoordination(node.getLocation()), aimCoordination) < minCost) {
                minCost = node.getCostFromStart();
                bestNode = node;
            }
        }
        if (!addAdjacent(bestNode)) {
            findPath();
            return;
        }
        createPath(bestNode);
    }

    public void createPath(Node lastNode) {
        boolean radiusChecked = false;
        pathToAim.add(Coordinate.getCoordination(lastNode.getLocation()));
        int parent = lastNode.getParentNode();
        while (closedList.containsKey(parent)) {
            Node next = closedList.get(parent);
            pathToAim.add(Coordinate.getCoordination(next.getLocation()));
            parent = next.getParentNode();
            if (!radiusChecked && !isInRadius(Coordinate.getCoordination(next.getLocation()))) {
                radiusChecked = true;
                inRadiusPath = pathToAim.size();
            }
        }
    }

    public boolean isInRadius(Coordinate current) {
        if (abs(pow(current.getX() - aimCoordination.getX(), 2) + pow(current.getY() - aimCoordination.getY(), 2) - pow(radius, 2)) < 0.2) {
            return true;
        }
        return false;
    }

    public boolean addAdjacent(Node bestNode) {
        closedList.put(bestNode.getLocation(), bestNode);
        openedList.remove(bestNode.getLocation());
        Coordinate inWork = Coordinate.getCoordination(bestNode.getLocation());
        for (int i = -1; i < 2; ++i) {
            for (int j = -1; j < 2; ++j) {
                if (i == j && i == 0)
                    continue;
                int locationX = inWork.getX() + i;
                int locationY = inWork.getY() + j;
                int location = Coordinate.toInt(locationX, locationY);
                if (location == aimNode.getLocation())
                    return true;
                if (locationX >= 0 && locationY >= 0 && locationX < limit && locationY < limit &&
                        !closedList.containsKey(location) && !openedList.containsKey(location)) {
                    if (unPassableList.containsKey(location)) {
                        inWay.add(unPassableList.get(location));
                        continue;
                    }
                    openedList.put(location, new Node(location, bestNode.getLocation(), bestNode.getCostFromStart() + 1));
                }
            }
        }
        return false;

    }

    public static int distanceCalculator(Coordinate startPoint, Coordinate endPoint) {
        // the reason that the function is returns the maximum is the diagonal path which is equal to the minimum
        int verticalDistance = abs(startPoint.getY() - endPoint.getY());
        int horizontalDistance = abs(startPoint.getX() - endPoint.getX());
        if (verticalDistance > horizontalDistance)
            return verticalDistance;
        return horizontalDistance;
    }
}