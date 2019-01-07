package com.imooc.product.controller;

import com.imooc.product.DTO.CartDTO;
import com.imooc.product.Utils.ResultVOUtil;
import com.imooc.product.VO.ProductInfoVO;
import com.imooc.product.VO.ProductVO;
import com.imooc.product.VO.ResultVO;
import com.imooc.product.dataobject.ProductCategory;
import com.imooc.product.dataobject.ProductInfo;
import com.imooc.product.repository.ProductInfoRepository;
import com.imooc.product.service.CategroyService;
import com.imooc.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping
    public ResultVO<ProductVO> list(){
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
    public List<ProductInfo> listForOrder(@RequestBody List<String> productIdList){
        return productService.findList(productIdList);
    }

    @PostMapping("/decreaseStock")
    public void decreaseStock(@RequestBody List<CartDTO> cartDTOList){
        productService.decreaseStock(cartDTOList);
    }
}
