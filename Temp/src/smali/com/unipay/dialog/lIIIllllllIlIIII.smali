.class Lcom/unipay/dialog/lIIIllllllIlIIII;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/view/View$OnTouchListener;


# instance fields
.field final synthetic _$1:Lcom/unipay/dialog/UniPayEnSureDialog;

.field final synthetic _$2:Landroid/widget/Button;


# direct methods
.method constructor <init>(Lcom/unipay/dialog/UniPayEnSureDialog;Landroid/widget/Button;)V
    .locals 0

    iput-object p1, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    iput-object p2, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onTouch(Landroid/view/View;Landroid/view/MotionEvent;)Z
    .locals 4

    const/4 v3, 0x0

    invoke-virtual {p2}, Landroid/view/MotionEvent;->getAction()I

    move-result v0

    packed-switch v0, :pswitch_data_0

    :pswitch_0
    :try_start_0
    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    iget-object v1, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-static {v1}, Lcom/unipay/dialog/UniPayEnSureDialog;->_$2(Lcom/unipay/dialog/UniPayEnSureDialog;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "unicom_base/close.png"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    const-string v2, "close.png"

    invoke-static {v1, v2}, Landroid/graphics/drawable/BitmapDrawable;->createFromStream(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V
    :try_end_0
    .catch Ljava/lang/Exception; {:try_start_0 .. :try_end_0} :catch_3

    :goto_0
    return v3

    :pswitch_1
    :try_start_1
    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    iget-object v1, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-static {v1}, Lcom/unipay/dialog/UniPayEnSureDialog;->_$2(Lcom/unipay/dialog/UniPayEnSureDialog;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "unicom_base/close_press.png"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    const-string v2, "close_press.png"

    invoke-static {v1, v2}, Landroid/graphics/drawable/BitmapDrawable;->createFromStream(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V
    :try_end_1
    .catch Ljava/lang/Exception; {:try_start_1 .. :try_end_1} :catch_0

    goto :goto_0

    :catch_0
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    :pswitch_2
    :try_start_2
    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    iget-object v1, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-static {v1}, Lcom/unipay/dialog/UniPayEnSureDialog;->_$2(Lcom/unipay/dialog/UniPayEnSureDialog;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "unicom_base/close.png"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    const-string v2, "close.png"

    invoke-static {v1, v2}, Landroid/graphics/drawable/BitmapDrawable;->createFromStream(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V
    :try_end_2
    .catch Ljava/lang/Exception; {:try_start_2 .. :try_end_2} :catch_1

    goto :goto_0

    :catch_1
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    :pswitch_3
    :try_start_3
    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    iget-object v1, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-static {v1}, Lcom/unipay/dialog/UniPayEnSureDialog;->_$2(Lcom/unipay/dialog/UniPayEnSureDialog;)Landroid/content/Context;

    move-result-object v1

    invoke-virtual {v1}, Landroid/content/Context;->getAssets()Landroid/content/res/AssetManager;

    move-result-object v1

    const-string v2, "unicom_base/close.png"

    invoke-virtual {v1, v2}, Landroid/content/res/AssetManager;->open(Ljava/lang/String;)Ljava/io/InputStream;

    move-result-object v1

    const-string v2, "close.png"

    invoke-static {v1, v2}, Landroid/graphics/drawable/BitmapDrawable;->createFromStream(Ljava/io/InputStream;Ljava/lang/String;)Landroid/graphics/drawable/Drawable;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/Button;->setBackgroundDrawable(Landroid/graphics/drawable/Drawable;)V
    :try_end_3
    .catch Ljava/lang/Exception; {:try_start_3 .. :try_end_3} :catch_2

    :goto_1
    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$2:Landroid/widget/Button;

    invoke-virtual {v0, v3}, Landroid/widget/Button;->setEnabled(Z)V

    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-static {v0}, Lcom/unipay/dialog/UniPayEnSureDialog;->_$3(Lcom/unipay/dialog/UniPayEnSureDialog;)Lcom/unipay/dialog/UniPayEnSureDialog$UniPayEnsureListenr;

    move-result-object v0

    const/4 v1, 0x2

    invoke-interface {v0, v1}, Lcom/unipay/dialog/UniPayEnSureDialog$UniPayEnsureListenr;->UniPayEnSureResult(I)V

    iget-object v0, p0, Lcom/unipay/dialog/lIIIllllllIlIIII;->_$1:Lcom/unipay/dialog/UniPayEnSureDialog;

    invoke-virtual {v0}, Lcom/unipay/dialog/UniPayEnSureDialog;->dismiss()V

    goto :goto_0

    :catch_2
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_1

    :catch_3
    move-exception v0

    invoke-virtual {v0}, Ljava/lang/Exception;->printStackTrace()V

    goto :goto_0

    :pswitch_data_0
    .packed-switch 0x0
        :pswitch_1
        :pswitch_3
        :pswitch_1
        :pswitch_0
        :pswitch_2
    .end packed-switch
.end method
