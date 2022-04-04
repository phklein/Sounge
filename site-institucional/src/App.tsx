import { BrowserRouter, Route, Routes } from "react-router-dom"

import { Home } from "./pages/Home"
import { PageNotFound } from "./pages/PageNotFound"
import { Register } from "./pages/Register"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/page-not-found" element={<PageNotFound />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App;
