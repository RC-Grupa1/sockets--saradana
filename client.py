import socket

def start_client(host,port):
    client_socket=socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        client_socket.connect((host,port))
        print("Conectat la server: {host}:{port}")
        while True:
            mesaj=input("Client:")
            mesaj_trimis=(mesaj+"\n").encode('utf-8')
            client_socket.sendall(mesaj_trimis)

            if mesaj.lower() == 'exit':
                print("Inchidere conexiune")
                break

            date_primite = client_socket.recv(1024)
            if not date_primite:
                print("Serverul a inchis conexiunea.")
                break
                
            mesaj = date_primite.decode('utf-8')
            print(f"Server: {mesaj}")

            if mesaj.lower() == 'exit':
                print("Serverul a solicitat inchiderea conexiunii.")
                break

    except ConnectionRefusedError:
        print("Eroare: Serverul nu este pornit sau portul este blocat.")
    except Exception as e:
        print(f"Eroare: {e}")
    finally:
        client_socket.close()

if __name__ == "__main__":
    start_client("100.87.217.112", 5000)