.class public Lcom/unionpay/upomp/bypay/other/A;
.super Ljava/lang/Object;

# interfaces
.implements Landroid/view/View$OnFocusChangeListener;


# instance fields
.field final synthetic a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;


# direct methods
.method public constructor <init>(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)V
    .locals 0

    iput-object p1, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    return-void
.end method


# virtual methods
.method public onFocusChange(Landroid/view/View;Z)V
    .locals 10

    const/4 v5, 0x0

    const/4 v1, 0x0

    const/4 v4, 0x1

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/Button;

    move-result-object v0

    if-ne p1, v0, :cond_2

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_2

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v1}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    invoke-virtual {p1}, Landroid/view/View;->showContextMenu()Z

    :cond_0
    :goto_0
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/RelativeLayout;

    move-result-object v0

    if-ne p1, v0, :cond_3

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_3

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v1}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    :cond_1
    :goto_1
    return-void

    :cond_2
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->b(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/Button;

    move-result-object v0

    if-ne p1, v0, :cond_0

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_0

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v1}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    invoke-virtual {p1}, Landroid/view/View;->showContextMenu()Z

    goto :goto_0

    :cond_3
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->c(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/Button;

    move-result-object v0

    if-ne p1, v0, :cond_4

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_4

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v4}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    sget-object v0, Lcom/unionpay/upomp/bypay/other/aC;->a:Landroid/content/Context;

    sget-object v1, Lcom/unionpay/upomp/bypay/other/aC;->a:Landroid/app/Activity;

    sget-object v2, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v3, "id"

    const-string v6, "btn_cvn2_content_auth_bind_card"

    invoke-static {v2, v3, v6}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    sget-object v2, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v3, "id"

    const-string v6, "btn_cvn2_content_auth_bind_card"

    invoke-static {v2, v3, v6}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    const/4 v3, 0x3

    iget-object v6, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-virtual {v6}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    sget-object v7, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v8, "string"

    const-string v9, "upomp_bypay_utils_cvn2_hint"

    invoke-static {v7, v8, v9}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v7

    invoke-virtual {v6, v7}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    move-object v6, v5

    invoke-static/range {v0 .. v7}, Lcom/unionpay/upomp/bypay/util/Utils;->a(Landroid/content/Context;Landroid/view/View;IIZLandroid/widget/EditText;Landroid/widget/Button;Ljava/lang/String;)V

    goto :goto_1

    :cond_4
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->d(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/Button;

    move-result-object v0

    if-ne p1, v0, :cond_5

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_5

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    const/4 v1, 0x2

    invoke-static {v0, v1}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    sget-object v0, Lcom/unionpay/upomp/bypay/other/aC;->a:Landroid/content/Context;

    sget-object v1, Lcom/unionpay/upomp/bypay/other/aC;->a:Landroid/app/Activity;

    sget-object v2, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v3, "id"

    const-string v6, "btn_pin_content_auth_bind_card"

    invoke-static {v2, v3, v6}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    invoke-virtual {v1, v2}, Landroid/app/Activity;->findViewById(I)Landroid/view/View;

    move-result-object v1

    sget-object v2, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v3, "id"

    const-string v6, "btn_pin_content_auth_bind_card"

    invoke-static {v2, v3, v6}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v2

    const/4 v3, 0x6

    iget-object v6, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-virtual {v6}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->getResources()Landroid/content/res/Resources;

    move-result-object v6

    sget-object v7, Lcom/unionpay/upomp/bypay/util/Utils;->a:Ljava/lang/String;

    const-string v8, "string"

    const-string v9, "upomp_bypay_utils_inputpsw_hint"

    invoke-static {v7, v8, v9}, Lcom/unionpay/upomp/bypay/util/Utils;->getResourceId(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)I

    move-result v7

    invoke-virtual {v6, v7}, Landroid/content/res/Resources;->getString(I)Ljava/lang/String;

    move-result-object v7

    move-object v6, v5

    invoke-static/range {v0 .. v7}, Lcom/unionpay/upomp/bypay/util/Utils;->a(Landroid/content/Context;Landroid/view/View;IIZLandroid/widget/EditText;Landroid/widget/Button;Ljava/lang/String;)V

    goto/16 :goto_1

    :cond_5
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/ImageButton;

    move-result-object v0

    if-ne p1, v0, :cond_6

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_6

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v4}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;Z)V

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v1}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->b(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/RelativeLayout;

    move-result-object v0

    iget-object v1, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v1}, Lcom/unionpay/upomp/bypay/util/Utils;->a(Landroid/content/Context;)Lcom/unionpay/upomp/bypay/view/ImageCvn2;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->addView(Landroid/view/View;)V

    invoke-static {}, Lcom/unionpay/upomp/bypay/util/Utils;->i()V

    goto/16 :goto_1

    :cond_6
    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->b(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/ImageButton;

    move-result-object v0

    if-ne p1, v0, :cond_1

    invoke-virtual {p1}, Landroid/view/View;->isFocused()Z

    move-result v0

    if-eqz v0, :cond_1

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v4}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;Z)V

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0, v4}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->a(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;I)V

    iget-object v0, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v0}, Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;->b(Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;)Landroid/widget/RelativeLayout;

    move-result-object v0

    iget-object v1, p0, Lcom/unionpay/upomp/bypay/other/A;->a:Lcom/unionpay/upomp/bypay/activity/AuthBindCardActivity;

    invoke-static {v1}, Lcom/unionpay/upomp/bypay/util/Utils;->a(Landroid/content/Context;)Lcom/unionpay/upomp/bypay/view/ImageCvn2;

    move-result-object v1

    invoke-virtual {v0, v1}, Landroid/widget/RelativeLayout;->addView(Landroid/view/View;)V

    invoke-static {}, Lcom/unionpay/upomp/bypay/util/Utils;->i()V

    goto/16 :goto_1
.end method
