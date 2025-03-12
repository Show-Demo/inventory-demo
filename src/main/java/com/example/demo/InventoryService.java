package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {
    private static final Logger logger = LoggerFactory.getLogger(InventoryService.class);

    // 模拟库存数据，商品ID -> 库存数量
    private Map<String, Integer> inventory = new HashMap<>();

    public InventoryService() {
        // 初始化库存数据
        inventory.put("item1", 10);
        inventory.put("item2", 5);
    }

    /**
     * 检查库存是否充足
     * @param itemId 商品ID
     * @param quantity 需要的数量
     * @return 如果库存充足返回 true，否则返回 false，并在日志中记录错误
     */
    public boolean checkInventory(String itemId, int quantity) {
        Integer available = inventory.get(itemId);
        if (available == null) {
            logger.error("商品 {} 没有库存记录", itemId);
            return false;
        }
        if (available < quantity) {
            logger.error("库存不足:商品 {} 需要 {} 件，但仅有 {} 件", itemId, quantity, available);
            return false;
        }
        return true;
    }

    /**
     * 扣减库存
     * @param itemId 商品ID
     * @param quantity 需要扣减的数量
     */
    public void reduceInventory(String itemId, int quantity) {
        if (checkInventory(itemId, quantity)) {
            inventory.put(itemId, inventory.get(itemId) - quantity);
            logger.info("扣减库存成功：商品 {} 扣减 {} 件，剩余 {} 件", itemId, quantity, inventory.get(itemId));
        } else {
            logger.error("无法扣减库存：商品 {} 库存不足", itemId);
        }
    }
}
