package com.buyerologie.web.trade;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.buyerologie.common.vo.JsonVO;
import com.buyerologie.enums.PayType;
import com.buyerologie.trade.TradeService;
import com.buyerologie.trade.exception.OrderNotExistException;
import com.buyerologie.trade.exception.TradeException;
import com.buyerologie.trade.model.TradeOrder;
import com.buyerologie.trade.pay.exception.PayException;
import com.buyerologie.user.exception.UserException;
import com.buyerologie.utils.QrCodeUtils;

@Controller
public class PayController {
    private static final Logger logger = Logger.getLogger(PayController.class);

    @Resource
    private TradeService        tradeService;

    @ResponseBody
    @RequestMapping(value = "/trade/pay/getqrcode", method = { RequestMethod.GET,
                                                               RequestMethod.POST })
    public void qrCode(Model model, @RequestParam String codeUrl,
                       HttpServletResponse response) throws UserException, TradeException,
                                                     PayException {

        BufferedImage image = QrCodeUtils.encodeQrcodeImage(codeUrl, 300, 300);

        try {
            ImageIO.write(image, "png", response.getOutputStream());
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    @RequestMapping(value = "/qrtest", method = { RequestMethod.GET, RequestMethod.POST })
    public String qrtest() throws UserException, TradeException, PayException {

        return "/page/trade/qrTest";
    }

    @ResponseBody
    @RequestMapping(value = "/trade/pay", method = { RequestMethod.GET, RequestMethod.POST })
    public String tradePay(Model model, @RequestParam long orderNumber) throws UserException,
                                                                        TradeException,
                                                                        PayException {

        TradeOrder tradeOrder = tradeService.get(orderNumber);
        if (tradeOrder == null) {
            throw new OrderNotExistException();
        }

        PayType payType = PayType.get(tradeOrder.getPayType());

        if (payType.equals(PayType.WEIXIN)) {
            JsonVO jsonVO = new JsonVO(true);
            jsonVO.setData(tradeService.trade(orderNumber));
            return jsonVO.toString();
        } else if (payType.equals(PayType.ALIPAY)) {
            tradeService.trade(orderNumber);
        } else {
            return "";
        }
        return "";
    }

    @ResponseBody
    @RequestMapping(value = "/trade/isPaid", method = { RequestMethod.GET, RequestMethod.POST })
    public String isPaid(@RequestParam long orderNumber) throws TradeException {

        TradeOrder tradeOrder = tradeService.get(orderNumber);
        if (tradeOrder == null) {
            throw new OrderNotExistException();
        }
        JsonVO jsonVO = new JsonVO(true);
        //    jsonVO.setData(true);
        jsonVO.setData(tradeOrder.getPaidTime() != null);
        return jsonVO.toString();
    }
}
