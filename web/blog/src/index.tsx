import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import reportWebVitals from "./reportWebVitals";
import Navigation from "./components/navigation/navigation";
import Footer from "./components/footer/footer";
import About from "./pages/about/about";
import Contact from "./pages/contact/contact";
import Home from "./pages/home/home";

ReactDOM.render(
  <Router>
    <Navigation />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/about" element={<About />} />
      <Route path="/contact" element={<Contact />} />
      {/* <Route path="/blog" element={<Blog />}>
        <Route path="" element={<Posts />} />
        <Route path=":postSlug" element={<Post />} />
      </Route> */}
    </Routes>
    <Footer />
  </Router>,

  document.getElementById("root")
);

reportWebVitals();
