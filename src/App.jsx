import "./App.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

function App() {
  return (
    <div>
      <h1 className="font-bold underline">Hello world</h1>
      <FontAwesomeIcon icon={faMagnifyingGlass} />
    </div>
  );
}

export default App;
