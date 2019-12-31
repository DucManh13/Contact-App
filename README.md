#include <graphics.h>
#include <stdio.h>
#include <string.h>
#include <math.h>
#define max 100000

struct element{
		char content[max];
		int repeat;
		int stt;
		element *left,*right;
};
typedef element* tree;
tree T;

FILE *f;

void create(tree &T){
	T=NULL;
}

void display(tree T){ //display theo thu tu giua
	if(T!=NULL){
		display((*T).left);
		printf("\t\t\t%d\r\t%s\n",(*T).repeat,(*T).content);
		display((*T).right);
	}
}

int insertnode(tree &T,char x[],int stt){  //them node vao cay
	if(T){
		if(strcmp(x,(*T).content)==0){	//da co thi tang repeat
			(*T).repeat++;
			return 0;
		}
		if(strcmp(x,(*T).content)>0){		// lon hon thi insert cay con phai
			return insertnode((*T).right,x,2*stt+2);
		}
		else{	// nho hon thi insert cay con trai
			return insertnode((*T).left,x,2*stt+1);
		}
	}
	T=new element;	// tao node moi
	if(T==NULL) return -1;	// khong du bo nho
	(*T).repeat=1;
	strcpy((*T).content,x);
	(*T).left=NULL;
	(*T).right=NULL;
	(*T).stt=stt;
	return 1;
}

tree search(tree T,char x[]){
	tree p=T;
	while(p!=NULL){
		if(strcmp(x,(*p).content)==0){
			printf("\n\tTu: %s\n",(*p).content);
			printf("\tSo lan xuat hien: %d \n",(*p).repeat);
			printf("\tThu tu trong cay: %d\n",(*p).stt);
			return p;
		} 
		else if(strcmp(x,(*p).content)<0) p=(*p).left;
		else p=(*p).right;
	}
	printf("\n\tKhong co tu can tim!!!\n");
	return NULL;
}

void replace(tree &p,tree &q){	// tim q chet thay cho p
	if((*q).left){	// tim cay con trai nhat cua q
		replace(p,(*q).left);
	}
	else{
		strcpy((*p).content,(*q).content);
		(*p).repeat=(*q).repeat;
		p=q;
		q=(*q).right;
		return;
	}	
}

void deletenode(tree &T,char x[]){
	if(T==NULL) return ;
	if(strcmp((*T).content,x)>0) return deletenode((*T).left,x);
	if(strcmp((*T).content,x)<0) return deletenode((*T).right,x);
	else{
		tree p=T;
		if((*T).left==NULL){
				T=(*T).right;
		} 
		else if((*T).right==NULL){
				T=(*T).left;
		} 
		else{
			tree q=(*T).right;
			replace(p,q);
		}
		delete p;
	}
}

void removetree(tree &T){	// xoa toan bo cay
	if(T){
		removetree((*T).left);
		removetree((*T).right);
		delete(T);
		T=NULL;
	}
}

tree skipnode(tree T,int i){ //tra ve nut co so thu tu la i,neu khong tra ve null
	int m=1,n=2,b;
	while(i>pow(2,m)-2) m++;	//m la muc cua nut can tim 
	while(true){
		b=((*T).stt)*2+1;	//thu tu cua nut con trai cua T
		for(int j=1;j<=m-n;j++) 
b=b*2+2;	//tinh thu tu lon nhat co the cua nut thuoc cay con trai
		if(i<=b){	//nut thu tu i nam tren cay con trai
			if((*T).left==NULL) return NULL;
			else T=(*T).left;
		}
		else{	//nut thu tu i nam tren cay con phai
			if((*T).right==NULL) return NULL;
			else T=(*T).right;
		} 
		n++;	//xuong muc tiep theo
		if(n>m) break;
	}
	return T;
}

int countlevel(tree T){	// tinh chieu cao cua cay
	if(T){
		if((*T).left==NULL&&(*T).right==NULL) return 1;
		else return (1+countlevel((*T).left)>=1+countlevel((*T).right)?
				1+countlevel((*T).left):1+countlevel((*T).right));
	}
	else return 0;
}

void readfile(FILE *f){	// doc tep dua tu vao cay
	create(T);
	char des[max],c;
	f=fopen("test.txt","r");	// mo file can doc
	int i=0;
	while(feof(f)==0){	// doc cho den cuoi file
		c=fgetc(f);		// doc 1 ky tu tu file
		if(feof(f)!=0){	// neu da den cuoi file 
			if(des[0]!=NULL) insertnode(T,des,0);	// doc tu tim duoc vao cay
		}
		else{
			if(('A'<=c&&c<='Z')||('a'<=c&&c<='z')||('0'<=c&&c<='9')){
 // neu ki tu la chu hoac so thi ghep vao tu 
				des[i]=c;
				i++;
			}
			else{
				if(des[0]!=NULL){	// them tu vua tim duoc vao cay 
					insertnode(T,des,0);
					for(int u=0;u<max;u++) des[u]=NULL;	
// reset chuoi des de tim tu tiep theo
					i=0;
				}
			}
		}
	}
	fclose(f);	// dong file
}

void graphics(tree T){	// ve cay
	tree tam=T;
	int i,j,n=1,stt=0,level=countlevel(T);
	initwindow(1400,600);
	setbkcolor(GREEN);
	cleardevice();  
	settextstyle(3,0,1);
	setlinestyle(0, 0, 2);
	int x=0,y=50,a=0,b=0,t=100,step=1200;
	outtextxy(680,20,(*T).content);	//in goc cua cay
	for(i=1;i<=level;i++){
		x=100+step/2;
		a=x-step/4;
		b=x+step/4;
		for(j=1;j<=n;j++){
			if(T!=NULL){
				if((*T).left!=NULL){	//in nut con trai neu co
					setcolor(BROWN);
					line(x,y,a,t);
					setcolor(WHITE);
					outtextxy(a-20,t,(*T).left->content);
				}
				if((*T).right!=NULL){	//in nut con phai neu co
					setcolor(BROWN);
					line(x,y,b,t);
					setcolor(WHITE);
					outtextxy(b-20,t,(*T).right->content);
				}
			}
			x+=step;	//dich toa do den nut tiep theo
			a+=step;b+=step;
			stt++;
			T=skipnode(tam,stt);	//tro den nut co thu tu ke tiep
		}
		step/=2;
		y+=80;t+=80;
		n*=2;	//so nut cua muc tiep theo	
	}
	getch();
	closegraph();	//dong so do cay 
}

void menu(){
	int chon;
	bool check=true;
	char buff[30];
	while(check){
		printf("\t\n---------------------MENU---------------------\n\n");
		printf("\t1. Duyet cay\n");
		printf("\t2. Them 1 tu vao cay\n");
		printf("\t3. Xoa 1 tu khoi cay\n");
		printf("\t4. Xoa toan bo cay\n");
		printf("\t5. Tim kiem tu trong cay\n");
		printf("\t6. Ve cay nhi phan tim kiem\n");
		printf("\t0. Thoat\n\n");
		printf("\tMoi chon chuc nang:");
		scanf("%d",&chon);
		printf("\n");
		switch(chon){
			case 1:
				if(T==NULL) printf("\tCay rong!!!\n");
				else {
					printf("\tCac tu co trong cay va so lan xuat hien:\n");
					display(T);
				}
				break;
			case 2:
				printf("\tMoi nhap tu muon them:");
				fflush(stdin); gets(buff);
				insertnode(T,buff,0);
				break;
			case 3:
				printf("\tMoi nhap tu muon xoa:");
				fflush(stdin); gets(buff);
				deletenode(T,buff);
				break;
			case 4:
				removetree(T);
				printf("\tDa xoa toan bo cay!!!\n");
				break;
			case 5:
				printf("\tMoi nhap tu can tim:");
				fflush(stdin); gets(buff);
				tree t;t=search(T,buff);
				break;
			case 6:
				if(T==NULL) printf("\tCay rong!!!\n");
				else graphics(T);
				break;
			case 0: 
				check=false;
				break;
			default:
				printf("\tSai cu phap, vui long nhap lai!!!\n");
				break;
		}
	}
}

int main(){
	system("color E9");
	readfile(f);
	menu();
	return 0;
}
