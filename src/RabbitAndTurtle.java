import java.util.Random;

class AnimalThread extends Thread {
    public String name;
    public int priority;
    public int countMeter = 0;// счетчик пройденных метров

    public AnimalThread(String name, int priority) {
        this.name = name;
        this.priority = priority;
        setPriority(priority);// устанавливаем первоначальный приоритет для потока, переданный через конструктор
    }

    @Override
    public void run() {
        Random random = new Random();
        // делаем забег 100 метров
        while (countMeter < 100) {
            countMeter++;
            System.out.println(name + " пробежал(а) " + countMeter + " метров");

            try {
                // рандомная задержка, чтоб потоки имели возможность обгонять друг друга
                Thread.sleep(random.nextInt(500));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        System.out.println(name + " финишировал!");
    }

}

public class RabbitAndTurtle {
    public static void main(String[] args) {
        // создание потоков
        AnimalThread rabbit = new AnimalThread("Кролик", 1);
        AnimalThread turtle = new AnimalThread("Черепаха", 1);

        // запуск потоков, метод start запускает потоков и вызывает метод run
        rabbit.start();
        turtle.start();

        // меняем приоритеты пока потоки выполняются
        while (rabbit.isAlive() && turtle.isAlive()) {
            // если один из потоков обгоняет другой, то меняем их приоритеты местами, чтобы отстающий поток догнал опережающий
            if (rabbit.countMeter > turtle.countMeter) {
                turtle.setPriority(10);
                rabbit.setPriority(1);

            } else if (turtle.countMeter > rabbit.countMeter) {
                rabbit.setPriority(10);
                turtle.setPriority(1);
            }
        }
    }
}