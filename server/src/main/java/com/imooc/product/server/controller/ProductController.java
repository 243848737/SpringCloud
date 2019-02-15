package com.imooc.product.server.controller;


import com.imooc.product.common.DecreaseStockInput;
import com.imooc.product.common.ProductInfoOutPut;
import com.imooc.product.server.DTO.CartDTO;
import com.imooc.product.server.Utils.ResultVOUtil;
import com.imooc.product.server.VO.ProductInfoVO;
import com.imooc.product.server.VO.ProductVO;
import com.imooc.product.server.VO.ResultVO;
import com.imooc.product.server.dataobject.ProductCategory;
import com.imooc.product.server.dataobject.ProductInfo;
import com.imooc.product.server.service.CategroyService;
import com.imooc.product.server.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController
{

    @Autowired
    private ProductService productService;

    @Autowired
    private CategroyService categroyService;

    /**
     * 1.查询所有在架商品
     * 2.获取类目type列表
     * 3.查询类目
     * 4.构造数据
     */
    @GetMapping("/list")
    public ResultVO<ProductVO> list(HttpServletRequest request){
        //1.查询所有在架商品
        List<ProductInfo> productInfoList=productService.findUpAll();

        //2.从所有商品 获取类目type列表
        List<Integer> categoryTypeList= productInfoList.stream()
                .map(ProductInfo::getCategoryType)
                .collect(Collectors.toList());

        //3.查询类目
        List<ProductCategory> categoryList=categroyService.findByCategoryTypeIn(categoryTypeList);

        //构造数据
        List<ProductVO> productVOList=new ArrayList<>();
        for (int i =0;i<categoryList.size();i++){
            ProductVO productVO=new ProductVO();
            productVO.setCategoryName(categoryList.get(i).getCategoryName());
            productVO.setCategoryType(categoryList.get(i).getCategoryType());

            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (int j=0;j<productInfoList.size();j++){
                if (productInfoList.get(j).getCategoryType().equals(categoryList.get(i).getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfoList.get(i), productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

        return ResultVOUtil.success(productVOList);
    }

    /**
     * 获取商品列表 （给订单服务使用）
     * @param productIdList
     * @return
     */
    @PostMapping("/listForOrder")
    public List<ProductInfoOutPut> listForOrder(@RequestBody List<String> productIdList){
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
         List<ProductInfoOutPut> list=productService.findList(productIdList);
        return list;
    }

    /**
     * 扣库存
     * @param cartDTOList
     */
    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<DecreaseStockInput> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }
}
