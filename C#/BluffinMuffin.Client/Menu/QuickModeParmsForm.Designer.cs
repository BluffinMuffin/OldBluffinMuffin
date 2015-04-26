using System.Windows.Forms;

namespace BluffinMuffin.Client.Menu
{
    partial class QuickModeParmsForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(QuickModeParmsForm));
            this.btnQuickMode = new Button();
            this.txtUsername = new System.Windows.Forms.TextBox();
            this.label2 = new System.Windows.Forms.Label();
            this.SuspendLayout();
            // 
            // btnQuickMode
            // 
            this.btnQuickMode.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.btnQuickMode.Image = global::BluffinMuffin.Client.Properties.Resources.learn;
            this.btnQuickMode.Location = new System.Drawing.Point(14, 74);
            this.btnQuickMode.Name = "btnQuickMode";
            this.btnQuickMode.Size = new System.Drawing.Size(353, 65);
            this.btnQuickMode.TabIndex = 0;
            this.btnQuickMode.Text = "Start QuickMode";
            this.btnQuickMode.TextImageRelation = System.Windows.Forms.TextImageRelation.ImageAboveText;
            this.btnQuickMode.UseVisualStyleBackColor = false;
            this.btnQuickMode.Click += new System.EventHandler(this.btnQuickMode_Click);
            // 
            // txtUsername
            // 
            this.txtUsername.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.txtUsername.Location = new System.Drawing.Point(14, 36);
            this.txtUsername.Name = "txtUsername";
            this.txtUsername.Size = new System.Drawing.Size(353, 23);
            this.txtUsername.TabIndex = 18;
            this.txtUsername.Text = "Player";
            this.txtUsername.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            this.txtUsername.TextChanged += new System.EventHandler(this.txtUsername_TextChanged);
            // 
            // label2
            // 
            this.label2.Font = new System.Drawing.Font("Microsoft Sans Serif", 10F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.label2.Location = new System.Drawing.Point(14, 9);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(353, 19);
            this.label2.TabIndex = 19;
            this.label2.Text = "Player Name:";
            this.label2.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // QuickModeParmsForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(381, 151);
            this.Controls.Add(this.txtUsername);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.btnQuickMode);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedDialog;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "QuickModeParmsForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "QuickMode ~ Bluffin Muffin Client";
            this.ResumeLayout(false);
            this.PerformLayout();

        }
        private Button btnQuickMode;
        private System.Windows.Forms.TextBox txtUsername;
        private System.Windows.Forms.Label label2;
        #endregion
    }
}