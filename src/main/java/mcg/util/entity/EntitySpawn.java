package mcg.util.entity;

import net.minecraft.entity.Entity;

import java.util.Stack;
import java.util.concurrent.locks.ReentrantLock;

public class EntitySpawn {

    public static class SafeStack<T> extends Stack<T> {

        private final ReentrantLock lock = new ReentrantLock();

        public SafeStack() { super(); }

        @Override
        public synchronized boolean isEmpty() {
            try {
                lock.lock();
                return super.isEmpty();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public T push(T item) {
            try {
                lock.lock();
                return super.push(item);
            } finally {
                lock.unlock();
            }
        }

        @Override
        public synchronized T pop() {
            try {
                lock.lock();
                return super.pop();
            } finally {
                lock.unlock();
            }
        }
    }

    public static class EntityInfo {

        public Entity entity;
        public int dimension;

        public EntityInfo() {
            entity = null;
            dimension = -1;
        }

        public EntityInfo(Entity e, int dim) {
            entity = e;
            dimension = dim;
        }

    }

    public static SafeStack<EntityInfo> ENTITY_STACK = new SafeStack<>();

    public static void addEntity(Entity entity) {
        ENTITY_STACK.push(new EntityInfo(entity, entity.dimension));
    }

}
