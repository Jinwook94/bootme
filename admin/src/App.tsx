import React, {useState} from "react";
import {Link, Routes, Route, useNavigate} from 'react-router-dom';
// @ts-ignore
import Modal from 'react-modal';
import {PASSWORD} from "./constants/password";
import AdminCourseCardPage from "./pages/AdminCourseCardPage";

const App = () => {
    const [password, setPassword] = useState()
    const navigate = useNavigate();

    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        if (password == PASSWORD) {
            navigate('/courseCard');
        }
    };

    const onChange = (e: any) => {
        setPassword(e.target.value);
    };

    return (
        <>
            <form onSubmit={handleSubmit}>
                <input type={"text"} placeholder={"입력하세요"} onChange={onChange} value={password}/>
                <button type="submit">Submit</button>
            </form>

            <Routes>
                <Route path="/courseCard" element={<AdminCourseCardPage />} />
            </Routes>
        </>
    )
}

export default App;