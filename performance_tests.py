import subprocess
import time
import statistics
import os

def run_performance_test(num_clients=1, iterations=50):
    print(f"[*] Starting performance test with {num_clients} client(s) and {iterations} iterations each.")
    
    # Ensure server is running or start it? 
    # For now, we assume the user starts the server in another terminal, 
    # OR we can try to start it here.
    
    # Let's try to start the server in the background if it's not running
    # This might be tricky in a script. Better to run it as a separate process.
    
    # We will just run the client script and capture its output or modify it to return data.
    # A better way is to import the client function.
    
    from client import run_client
    import threading

    all_latencies = []
    threads = []

    def client_task():
        lats = run_client(iterations=iterations)
        if lats:
            all_latencies.extend(lats)

    for i in range(num_clients):
        t = threading.Thread(target=client_task)
        threads.append(t)
        t.start()

    for t in threads:
        t.join()

    if all_latencies:
        print("\n--- Final Performance Report ---")
        print(f"Total commands sent: {len(all_latencies)}")
        print(f"Minimum Latency: {min(all_latencies):.2f}ms")
        print(f"Maximum Latency: {max(all_latencies):.2f}ms")
        print(f"Average Latency: {statistics.mean(all_latencies):.2f}ms")
        print(f"Median Latency: {statistics.median(all_latencies):.2f}ms")
        print(f"Std Dev Latency: {statistics.stdev(all_latencies):.2f}ms" if len(all_latencies) > 1 else "Std Dev: N/A")
    else:
        print("[-] No data collected.")

if __name__ == "__main__":
    # Note: Make sure server.py is running before executing this.
    run_performance_test(num_clients=2, iterations=20)
