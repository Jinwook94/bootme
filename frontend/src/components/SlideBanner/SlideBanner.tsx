import React from "react";
import {Swiper, SwiperSlide, SwiperProps, SwiperSlideProps} from 'swiper/react';
import SwiperCore, {Autoplay} from 'swiper';
import 'swiper/css';
import "./SlideBanner.styles.css"

SwiperCore.use([Autoplay]);


const SlideBanner = () => {
    return (
        <>
            <div className="swiper-container">
                <Swiper className="swiper-container"
                        spaceBetween={10}
                        loop={true}
                        autoplay={{
                            delay: 5000,
                            disableOnInteraction: false
                        }}>
                    <SwiperSlide>
                        <a href={"https://career.programmers.co.kr/companies/2653?itm_source=coupang10&amp;itm_source=prgms&amp;itm_medium=banner&amp;itm_campaign=index_2&amp;itm_content=banner_id_4053_display_order_2.0&amp;source=career.programmers.co.kr%2Fjob"}
                           target='_blank'>
                            <div className="banner-item" style={{background: "#E1F5FE"}}>
                                <div className="banner-item-wrapper">
                                    <img className="banner-item-thumbnail"
                                         src={"https://asset.programmers.co.kr/image/origin/production/banner/164069/ac55111a-69e5-4c20-abd8-db5fa983b57c.png"}
                                         alt={"프론트엔드 이미지"}/>
                                </div>
                                <div className="banner-item-content">
                                    <div className="banner-item-content-link">
                                        <header className="post-card-header">
                                            <div className="banner-item-tag">{"쿠팡"}</div>
                                            <h3 className="banner-item-title">{"쿠팡의 미래를 만들다, Business Analyst."} </h3>
                                            <p className="font-weight-500">{"지금 지원하세요."}
                                            </p>
                                        </header>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </SwiperSlide>
                    <SwiperSlide>
                        <a href={"https://career.programmers.co.kr/companies/1461?itm_source=haezoom09&amp;itm_source=prgms&amp;itm_medium=banner&amp;itm_campaign=index_1&amp;itm_content=banner_id_3987_display_order_1.0&amp;source=career.programmers.co.kr%2Fjob"}
                           target='_blank'>
                            <div className="banner-item" style={{background: "#FFF3E0"}}>
                                <div className="banner-item-wrapper">
                                    <img className="banner-item-thumbnail"
                                         src={"https://asset.programmers.co.kr/image/origin/production/banner/163926/3794909f-2f4c-4782-b724-32c0041f035a.png"}
                                         alt={"프론트엔드 이미지"}/>
                                </div>
                                <div className="banner-item-content">
                                    <div className="banner-item-content-link">
                                        <header className="post-card-header">
                                            <div className="banner-item-tag">{"해줌"}</div>
                                            <h3 className="banner-item-title">{"재생에너지를 최대 전력원으로!"} </h3>
                                            <p className="font-weight-500 mb-0 color-blue-grey-700">{"에너지IT기업 해줌에서 미션을 함께할 개발자를 찾습니다."}
                                            </p>
                                        </header>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </SwiperSlide>
                </Swiper>

            </div>
        </>
    );
}

export default SlideBanner;