import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class FizzBuzz {
    private int n;
    private int current = 1;

    private Semaphore sem = new Semaphore(1);
    private Semaphore fizzSem = new Semaphore(0);
    private Semaphore buzzSem = new Semaphore(0);
    private Semaphore fizzBuzzSem = new Semaphore(0);
    private Semaphore numberSem = new Semaphore(0);

    public FizzBuzz(int n) {
        this.n = n;
    }

    private void next() {
        current++;

        if (current > n) {
            fizzSem.release();
            buzzSem.release();
            fizzBuzzSem.release();
            numberSem.release();
            return;
        }

        if (current % 3 == 0 && current % 5 == 0) {
            fizzBuzzSem.release();
        } else if (current % 3 == 0) {
            fizzSem.release();
        } else if (current % 5 == 0) {
            buzzSem.release();
        } else {
            numberSem.release();
        }
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while (true) {
            fizzSem.acquire();
            if (current > n) break;
            printFizz.run();
            sem.release();
            next();
        }
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while (true) {
            buzzSem.acquire();
            if (current > n) break;
            printBuzz.run();
            sem.release();
            next();
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while (true) {
            fizzBuzzSem.acquire();
            if (current > n) break;
            printFizzBuzz.run();
            sem.release();
            next();
        }
    }

    // printNumber.accept(x) outputs "x".
    public void number(IntConsumer printNumber) throws InterruptedException {
        while (true) {
            sem.acquire();

            if (current > n) {
                fizzSem.release();
                buzzSem.release();
                fizzBuzzSem.release();
                break;
            }

            if (current % 3 == 0 && current % 5 == 0) {
                fizzBuzzSem.release();
            } else if (current % 3 == 0) {
                fizzSem.release();
            } else if (current % 5 == 0) {
                buzzSem.release();
            } else {
                printNumber.accept(current);
                next();
                sem.release();
            }
        }
    }
}
   