public class ImageTest {
    public static void main(String[] args) {
        System.out.println("Testing image loading...");
        
        System.out.println("Player tank up: " + (pictures.playerTankUp != null));
        System.out.println("Player tank down: " + (pictures.playerTankDown != null));
        System.out.println("Enemy1 tank up: " + (pictures.enermy1TankUp != null));
        System.out.println("Enemy2 tank up: " + (pictures.enermy2TankUp != null));
        
        System.out.println("Grass: " + (pictures.grass != null));
        System.out.println("Water: " + (pictures.water != null));
        System.out.println("Steel: " + (pictures.steel != null));
        System.out.println("Steels: " + (pictures.steels != null));
        System.out.println("SteelsCrosswise: " + (pictures.steelsCrosswise != null));
        System.out.println("SteelsVertical: " + (pictures.steelsVertical != null));
        System.out.println("Wall: " + (pictures.wall != null));
        System.out.println("Walls: " + (pictures.walls != null));
        System.out.println("Tank Missile: " + (pictures.tankMissile != null));
    }
}