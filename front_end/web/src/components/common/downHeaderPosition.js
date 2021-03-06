import React from "react";
import {Link} from "react-router-dom"
import "../../assets/css/Common/common.css"
import "../../assets/css/Common/header.css";
import HOME_ICON from "../../assets/images/common/house.png";
import CREW_ICON from "../../assets/images/common/community.png";
import MEDAL_ICON from "../../assets/images/common/match.png";
import SETTING_ICON from "../../assets/images/common/settings.png";


function DownHeaderPosition() {
  return (
    <div className="down_header_position_absolute">
      <Link to="/home" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"><img src={HOME_ICON}/></div>
          <div className="down_header_content_text">홈</div>
        </div>  
      </Link>
      <Link to="/matching" className="common_link">
        <div className="down_header_content">
          <div className="down_header_content_img"><img src={MEDAL_ICON}/></div>
          <div className="down_header_content_text">매칭</div>
        </div>  
      </Link>
      <Link to="/MyCrew" className="common_link">
        <div className="down_header_content_right down_header_content">
          <div className="down_header_content_img"><img src={SETTING_ICON}/></div>
          <div className="down_header_content_text">관리</div>
        </div>  
      </Link>
      <Link to="/crew" className="common_link">
        <div className="down_header_content down_header_content_right">
          <div className="down_header_content_img"><img src={CREW_ICON}/></div>
          <div className="down_header_content_text">크루</div>
        </div>  
      </Link>
    </div>
  );
}

export default DownHeaderPosition;