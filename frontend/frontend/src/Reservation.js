import React from 'react';
import {Button} from 'react-bootstrap';
import ReactDOM from 'react-dom/client';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import 'bootstrap/dist/css/bootstrap.min.css';
import SiteNav from './SiteNav';
import Home from './Home';
import {backend} from './Constants';

const { Component } = React
var root = ReactDOM.createRoot(document.getElementById('root'));

class Reservation extends Component {
  constructor(props){
    super(props)
    this.state = {
      userId: this.props.userId,
      data: []
    }
    fetch(backend + '/yourReservations', {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type':'application/json'},
        body: JSON.stringify({ user_id: this.state.userId })
       })
        .then(response => response.json())
        .then(data => {
            var tmp = [];
            for (var i=0; i < data.result.length; i++) {
              tmp.push(JSON.parse(data.result[i].chars));
            }
            this.setState({ data: tmp });
        });
  }

  cancelEvent = (event) => {
    var book_key = event.target.value;
    var book_id = this.state.data[book_key]._id["$oid"];
    fetch(backend + '/cancelBook', {
        method: 'POST',
        headers: {'Accept': 'application/json', 'Content-Type':'application/json'},
        body: JSON.stringify({ user_id: this.state.userId, book_id: book_id })
       })
        .then(response => response.json())
        .then(data => {
        });
    root.render(<Home userId={this.state.userId} />)
  }

  render() {
    return (
        <>
        <SiteNav userId={this.state.userId}/>
        <div style={{textAlign: "center", marginTop: "5px", marginLeft: "30px", marginRight: "30px"}}>
            <h1 style={{marginBottom: "5px"}}>Twoje rezerwacje</h1>
            {this.state.data.length === 0
            ? <div>Nie posiadasz żadnych rezerwacji</div>
            : <TableContainer component={Paper}>
            <Table sx={{ minWidth: 650 }} size="small" aria-label="a dense table">
                <TableHead>
                <TableRow>
                    <TableCell>ID ksążki</TableCell>
                    <TableCell align="right">Autor</TableCell>
                    <TableCell align="right">Tytuł</TableCell>
                    <TableCell align="right">Akcja</TableCell>
                </TableRow>
                </TableHead>
                <TableBody>
                {this.state.data.map((row, key) => (
                    <TableRow
                    key={key}
                    sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                    >
                    <TableCell component="th" scope="row">
                        {key}
                    </TableCell>
                    <TableCell align="right">{row.author}</TableCell>
                    <TableCell align="right">{row.title}</TableCell>
                    <TableCell align="right"><Button value={key} onClick={this.cancelEvent}>Anuluj rezerwacje</Button></TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
        </TableContainer>
            }
        </div>
        </>
    )
  }
}

export default Reservation;