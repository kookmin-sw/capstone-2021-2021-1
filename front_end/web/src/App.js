import React from "react";
import { HashRouter, Route} from "react-router-dom";
import Home from "./screen/home/Home";
import Matching from "./screen/matching/Matching";
import Login from "./screen/login/Login";
import Enroll from "./screen/Enrollment/Enrollment"
function App(){
  return (
    <HashRouter>
      <Route path="/" exact={true} component={Home}/>
      <Route path="/matching" exact={true} component={Matching}/>
      <Route path="/login" exact={true} component={Login}/>
      <Route path="/enroll" exact={true} component={Enroll}/>
    </HashRouter>
    );
}

export default App;

