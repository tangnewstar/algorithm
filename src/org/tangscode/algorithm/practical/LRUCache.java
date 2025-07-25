package org.tangscode.algorithm.practical;

import java.util.HashMap;
import java.util.Map;

// 双向链表节点
class DLinkedNode {
    int key;
    int value;
    DLinkedNode prev;
    DLinkedNode next;
    
    public DLinkedNode() {}
    
    public DLinkedNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

public class LRUCache {
    // 哈希表：用于快速定位节点
    private Map<Integer, DLinkedNode> cache;
    // 双向链表：用于维护节点的访问顺序
    private DLinkedNode head, tail;
    // 缓存容量
    private int capacity;
    // 当前缓存大小
    private int size;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.cache = new HashMap<>();
        
        // 初始化哨兵节点，简化边界处理
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }
    
    // 获取缓存中的值
    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        
        // 将访问的节点移到头部（表示最近使用）
        moveToHead(node);
        return node.value;
    }
    
    // 向缓存中放入键值对
    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        
        if (node == null) {
            // 键不存在，创建新节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            cache.put(key, newNode);
            addToHead(newNode);
            size++;
            
            // 如果超出容量，删除最久未使用的节点（尾部节点）
            if (size > capacity) {
                DLinkedNode tailNode = removeTail();
                cache.remove(tailNode.key);
                size--;
            }
        } else {
            // 键已存在，更新值并移到头部
            node.value = value;
            moveToHead(node);
        }
    }
    
    // 将节点添加到头部
    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }
    
    // 移除指定节点
    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
    
    // 将节点移到头部（先移除再添加）
    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }
    
    // 移除尾部节点（最久未使用）
    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
    
    // 测试方法
    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1，缓存变为 {2=2, 1=1}
        lRUCache.put(3, 3); // 该操作会使关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使关键字 1 作废，缓存是 {3=3, 4=4}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4
    }
}
