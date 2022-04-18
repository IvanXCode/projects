#define P 256

__global__ void kernel(float *a, float *b, int c){

	int tid = threadIdx.x + blockIdx.x * blockDim.x;
	__shared__ s_A;
	
	if(tid < n){
		
		if(P*P < n){
			int pom = (n / P*P);
			for(int i = 0; i < pom; i++){
				s_A[tid*pom + i] = a[tid*pom + i]; 
				__syncthreads();
			}
			int pom2 = n % P*P;
			if(tid < pom2){
				s_A[P*P*pom + tid] = a[P*P*pom + tid];
				__syncthreads();
			}
			
			
			if(tid < (n-2)){
				if(P*P >= (n-2)){
					b[tid] = (2*s_A[tid] + 3*s_A[tid+1] + 4*s_A[tid+2])/9.f;
				}
				else{
					int pom3 = (n-2) / P*P;
					int pom4 = (n-2) % P*P;
					for(int i = 0; i < pom3; i++){
						b[tid*pom3 + i] = (2*s_A[tid*pom3 + i] + 3*s_A[tid*pom3 + i +1] + 4*s_A[tid*pom3 + i +2])/9.f;
					}
					if(tid < pom4){
						b[P*P*pom3 + tid] = (2*s_A[P*P*pom3 + tid] + 3*s_A[P*P*pom3 + tid + 1] + 4*s_A[P*P*pom3 + tid +2])/9.f;
					}
				}
			}
		}
		else{
			s_A[tid] = a[tid];
			__syncthreads();
			
			if(tid < n-2){
				b[tid] = (2*s_A[tid] + 3*s_A[tid+1] + 4*s_A[tid+2])/9.f;
			}
		}
	}
}

__host__ int main(){

	int n;
	float *h_A, *h_B;
	float *d_A, *d_B;
	
	scanf("%d", &n);
	h_A = (int*)malloc(n * sizeof(float));
	h_B = (int*)malloc(n * sizeof(float));
	
	for(int i = 0; i < n; i++){
		h_A[i] = rand() % 10;
	}
	
	cudaMalloc((void**)&d_A, n * sizeof(float));
	cudaMalloc((void**)&d_B, n * sizeof(float));
	
	cudaMemcpy(d_A, h_A, n * sizeof(float), cudaMemcpyHostToDevice);
	
	kernel<<< P, P >>>(d_A,d_B,n);
	
	cudaMemcpy(h_B, d_B, n * sizeof(float), cudaMemcpyDeviceToHost);
	
	free(h_A);
	free(h_B);
	cudaFree(d_A);
	cudaFree(d_B);
	return 0;
{
