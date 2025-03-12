package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private InventoryService inventoryService;

    /**
     * 模拟下订单操作
     * URL 示例：POST /orders/create?itemId=item1&quantity=15
     * @param itemId 商品ID
     * @param quantity 订购数量
     * @return 订单处理结果
     */
    @PostMapping("/create")
    public String createOrder(@RequestParam String itemId, @RequestParam int quantity) {
        boolean sufficient = inventoryService.checkInventory(itemId, quantity);
        if (!sufficient) {
            // 尽管接口返回200，但内部已记录日志并返回业务错误提示
            return "订单处理失败，库存不足";
        }
        inventoryService.reduceInventory(itemId, quantity);
        return "订单创建成功";
    }
}
