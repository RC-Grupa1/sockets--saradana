import socket

def start_client_udp(host, port):
    client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    server_address = (host, port)

    print(f"Mesajele vor fi trimise către {host}:{port} via UDP")

    try:
        while True:
            mesaj = input("Client: ")
            client_socket.sendto(mesaj.encode('utf-8'), server_address)

            if mesaj.lower() == 'exit':
                break

           
            date_primite, _ = client_socket.recvfrom(4096)
            mesaj_server = date_primite.decode('utf-8').strip()
            
            print(f"Server: {mesaj_server}")

            if mesaj_server.lower() == 'exit':
                print("Serverul a inchis conversatia.")
                break

    except Exception as e:
        print(f"Eroare: {e}")
    finally:
        client_socket.close()
        print("Socket inchis.")

if __name__ == "__main__":
    start_client_udp("100.87.217.112", 5000)