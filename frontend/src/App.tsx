import Button from "./components/@common/Button/Button";
import React from "react";
import SlideBanner from "./components/SlideBanner/SlideBanner";
import CourseCard from "./components/CourseCard/CourseCard";


const App = () => {
    return <div>
        <div>bootMe</div>
        <div>부트미</div>
        <Button>버튼</Button>
        <Button>button</Button>
        <SlideBanner/>
        <ul style={{display: "flex", flexWrap:"wrap"}}>
            <CourseCard/>
            <CourseCard/>
        </ul>
    </div>

};

export default App;